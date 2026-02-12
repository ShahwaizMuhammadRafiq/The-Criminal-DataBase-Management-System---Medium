package com.cdms.service;

import com.cdms.model.FIR;
import com.cdms.model.Officer;
import com.cdms.repository.FIRRepository;
import com.cdms.repository.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * FIR Service
 * 
 * Software Construction Features:
 * - Ordered locking to prevent deadlocks (FIR lock before Officer lock)
 * - Synchronized assignment operations
 * - Thread-safe status transitions
 */
@Service
public class FIRService {
    
    @Autowired
    private FIRRepository firRepository;
    
    @Autowired
    private OfficerRepository officerRepository;
    
    @Autowired
    private AuditService auditService;
    
    // Ordered locks for deadlock prevention
    // Always acquire firLock before officerLock
    private final Lock firLock = new ReentrantLock(true);
    private final Lock officerLock = new ReentrantLock(true);
    
    public FIR createFIR(FIR fir, String creatorUsername, Long creatorId, String ipAddress) {
        firLock.lock();
        try {
            fir.setCreatedAt(LocalDateTime.now());
            fir.setUpdatedAt(LocalDateTime.now());
            fir.setCreatedBy(creatorId);
            fir.setUpdatedBy(creatorId);
            fir.setStatus(FIR.FIRStatus.REGISTERED);
            
            Long id = firRepository.peekNextId();
            fir.setId(id);
            FIR saved = firRepository.save(fir, id);
            
            auditService.logAction("FIR", id, "CREATE", creatorUsername, creatorId,
                "Created FIR: " + fir.getFirNumber(), ipAddress);
            
            return saved;
        } finally {
            firLock.unlock();
        }
    }
    
    public FIR getFIRById(Long id) {
        return firRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("FIR not found: " + id));
    }
    
    public FIR getFIRByNumber(String firNumber) {
        return firRepository.findByFirNumber(firNumber)
            .orElseThrow(() -> new IllegalArgumentException("FIR not found: " + firNumber));
    }
    
    public List<FIR> getAllFIRs() {
        return firRepository.findAll();
    }
    
    public List<FIR> getFIRsByCriminalId(Long criminalId) {
        return firRepository.findByCriminalId(criminalId);
    }
    
    public List<FIR> getFIRsByOfficerId(Long officerId) {
        return firRepository.findByOfficerId(officerId);
    }
    
    public List<FIR> getFIRsByStatus(FIR.FIRStatus status) {
        return firRepository.findByStatus(status);
    }
    
    /**
     * Assign FIR to officer with deadlock-safe locking
     * 
     * Deadlock Prevention Strategy:
     * - Always acquire locks in same order: firLock -> officerLock
     * - Never nest locks in reverse order
     * - Release locks in reverse order
     */
    public FIR assignToOfficer(Long firId, Long officerId, String assignerUsername, Long assignerId, String ipAddress) {
        // Acquire locks in consistent order to prevent deadlock
        firLock.lock();
        try {
            officerLock.lock();
            try {
                FIR fir = getFIRById(firId);
                Officer officer = officerRepository.findById(officerId)
                    .orElseThrow(() -> new IllegalArgumentException("Officer not found: " + officerId));
                
                // Update FIR
                Long previousOfficerId = fir.getAssignedOfficerId();
                fir.setAssignedOfficerId(officerId);
                fir.setStatus(FIR.FIRStatus.UNDER_INVESTIGATION);
                fir.setUpdatedAt(LocalDateTime.now());
                fir.setUpdatedBy(assignerId);
                
                // Update officer counts
                officer.setActiveFIRs(officer.getActiveFIRs() + 1);
                officerRepository.update(officerId, officer);
                
                // If previously assigned, decrement old officer's count
                if (previousOfficerId != null && !previousOfficerId.equals(officerId)) {
                    officerRepository.findById(previousOfficerId).ifPresent(prevOfficer -> {
                        prevOfficer.setActiveFIRs(Math.max(0, prevOfficer.getActiveFIRs() - 1));
                        officerRepository.update(previousOfficerId, prevOfficer);
                    });
                }
                
                FIR updated = firRepository.update(firId, fir);
                
                auditService.logAction("FIR", firId, "ASSIGN", assignerUsername, assignerId,
                    "Assigned to officer: " + officer.getName(), ipAddress);
                
                return updated;
            } finally {
                officerLock.unlock();
            }
        } finally {
            firLock.unlock();
        }
    }
    
    public FIR updateFIR(Long id, FIR fir, String updaterUsername, Long updaterId, String ipAddress) {
        firLock.lock();
        try {
            FIR existing = getFIRById(id);
            
            existing.setComplainantName(fir.getComplainantName());
            existing.setComplainantContact(fir.getComplainantContact());
            existing.setComplainantAddress(fir.getComplainantAddress());
            existing.setIncidentDateTime(fir.getIncidentDateTime());
            existing.setIncidentLocation(fir.getIncidentLocation());
            existing.setIncidentDescription(fir.getIncidentDescription());
            existing.setOffenseType(fir.getOffenseType());
            existing.setSections(fir.getSections());
            existing.setInvestigationNotes(fir.getInvestigationNotes());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy(updaterId);
            
            FIR updated = firRepository.update(id, existing);
            
            auditService.logAction("FIR", id, "UPDATE", updaterUsername, updaterId,
                "Updated FIR: " + existing.getFirNumber(), ipAddress);
            
            return updated;
        } finally {
            firLock.unlock();
        }
    }
    
    public FIR updateStatus(Long id, FIR.FIRStatus status, String updaterUsername, Long updaterId, String ipAddress) {
        firLock.lock();
        try {
            FIR fir = getFIRById(id);
            fir.setStatus(status);
            fir.setUpdatedAt(LocalDateTime.now());
            fir.setUpdatedBy(updaterId);
            
            FIR updated = firRepository.update(id, fir);
            
            auditService.logAction("FIR", id, "STATUS_UPDATE", updaterUsername, updaterId,
                "Changed status to: " + status, ipAddress);
            
            return updated;
        } finally {
            firLock.unlock();
        }
    }
    
    public void deleteFIR(Long id, String deleterUsername, Long deleterId, String ipAddress) {
        FIR fir = getFIRById(id);
        firRepository.delete(id);
        
        auditService.logAction("FIR", id, "DELETE", deleterUsername, deleterId,
            "Deleted FIR: " + fir.getFirNumber(), ipAddress);
    }
}
