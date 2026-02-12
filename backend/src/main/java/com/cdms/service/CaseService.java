package com.cdms.service;

import com.cdms.model.Case;
import com.cdms.model.Officer;
import com.cdms.repository.CaseRepository;
import com.cdms.repository.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Case Service
 * 
 * Software Construction Features:
 * - Ordered locking (caseLock before officerLock)
 * - Synchronized multi-resource operations
 * - Deadlock prevention through consistent lock ordering
 */
@Service
public class CaseService {
    
    @Autowired
    private CaseRepository caseRepository;
    
    @Autowired
    private OfficerRepository officerRepository;
    
    @Autowired
    private AuditService auditService;
    
    // Ordered locks - same order as FIRService for global deadlock prevention
    private final Lock caseLock = new ReentrantLock(true);
    private final Lock officerLock = new ReentrantLock(true);
    
    public Case createCase(Case caseEntity, String creatorUsername, Long creatorId, String ipAddress) {
        caseLock.lock();
        try {
            caseEntity.setCreatedAt(LocalDateTime.now());
            caseEntity.setUpdatedAt(LocalDateTime.now());
            caseEntity.setCreatedBy(creatorId);
            caseEntity.setUpdatedBy(creatorId);
            caseEntity.setStatus(Case.CaseStatus.FILED);
            
            Long id = caseRepository.peekNextId();
            caseEntity.setId(id);
            Case saved = caseRepository.save(caseEntity, id);
            
            auditService.logAction("Case", id, "CREATE", creatorUsername, creatorId,
                "Created case: " + caseEntity.getCaseNumber(), ipAddress);
            
            return saved;
        } finally {
            caseLock.unlock();
        }
    }
    
    public Case getCaseById(Long id) {
        return caseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Case not found: " + id));
    }
    
    public Case getCaseByCaseNumber(String caseNumber) {
        return caseRepository.findByCaseNumber(caseNumber)
            .orElseThrow(() -> new IllegalArgumentException("Case not found: " + caseNumber));
    }
    
    public List<Case> getAllCases() {
        return caseRepository.findAll();
    }
    
    public List<Case> getCasesByOfficerId(Long officerId) {
        return caseRepository.findByOfficerId(officerId);
    }
    
    public List<Case> getCasesByStatus(Case.CaseStatus status) {
        return caseRepository.findByStatus(status);
    }
    
    public List<Case> getCasesByCriminalId(Long criminalId) {
        return caseRepository.findByCriminalId(criminalId);
    }
    
    /**
     * Assign case to investigating officer
     * Uses ordered locking to prevent deadlock
     */
    public Case assignToOfficer(Long caseId, Long officerId, String assignerUsername, Long assignerId, String ipAddress) {
        caseLock.lock();
        try {
            officerLock.lock();
            try {
                Case caseEntity = getCaseById(caseId);
                Officer officer = officerRepository.findById(officerId)
                    .orElseThrow(() -> new IllegalArgumentException("Officer not found: " + officerId));
                
                Long previousOfficerId = caseEntity.getInvestigatingOfficerId();
                caseEntity.setInvestigatingOfficerId(officerId);
                caseEntity.setUpdatedAt(LocalDateTime.now());
                caseEntity.setUpdatedBy(assignerId);
                
                // Update officer counts
                officer.setActiveCases(officer.getActiveCases() + 1);
                officerRepository.update(officerId, officer);
                
                // Decrement previous officer's count
                if (previousOfficerId != null && !previousOfficerId.equals(officerId)) {
                    officerRepository.findById(previousOfficerId).ifPresent(prevOfficer -> {
                        prevOfficer.setActiveCases(Math.max(0, prevOfficer.getActiveCases() - 1));
                        officerRepository.update(previousOfficerId, prevOfficer);
                    });
                }
                
                Case updated = caseRepository.update(caseId, caseEntity);
                
                auditService.logAction("Case", caseId, "ASSIGN", assignerUsername, assignerId,
                    "Assigned to officer: " + officer.getName(), ipAddress);
                
                return updated;
            } finally {
                officerLock.unlock();
            }
        } finally {
            caseLock.unlock();
        }
    }
    
    public Case updateCase(Long id, Case caseEntity, String updaterUsername, Long updaterId, String ipAddress) {
        caseLock.lock();
        try {
            Case existing = getCaseById(id);
            
            existing.setTitle(caseEntity.getTitle());
            existing.setDescription(caseEntity.getDescription());
            existing.setCriminalIds(caseEntity.getCriminalIds());
            existing.setFirIds(caseEntity.getFirIds());
            existing.setFilingDate(caseEntity.getFilingDate());
            existing.setCourt(caseEntity.getCourt());
            existing.setJudge(caseEntity.getJudge());
            existing.setProsecutorName(caseEntity.getProsecutorName());
            existing.setDefenseAdvocate(caseEntity.getDefenseAdvocate());
            existing.setHearingDate(caseEntity.getHearingDate());
            existing.setVerdict(caseEntity.getVerdict());
            existing.setEvidenceList(caseEntity.getEvidenceList());
            existing.setWitnessDetails(caseEntity.getWitnessDetails());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy(updaterId);
            
            Case updated = caseRepository.update(id, existing);
            
            auditService.logAction("Case", id, "UPDATE", updaterUsername, updaterId,
                "Updated case: " + existing.getCaseNumber(), ipAddress);
            
            return updated;
        } finally {
            caseLock.unlock();
        }
    }
    
    public Case updateStatus(Long id, Case.CaseStatus status, String updaterUsername, Long updaterId, String ipAddress) {
        caseLock.lock();
        try {
            Case caseEntity = getCaseById(id);
            caseEntity.setStatus(status);
            caseEntity.setUpdatedAt(LocalDateTime.now());
            caseEntity.setUpdatedBy(updaterId);
            
            Case updated = caseRepository.update(id, caseEntity);
            
            auditService.logAction("Case", id, "STATUS_UPDATE", updaterUsername, updaterId,
                "Changed status to: " + status, ipAddress);
            
            return updated;
        } finally {
            caseLock.unlock();
        }
    }
    
    public void deleteCase(Long id, String deleterUsername, Long deleterId, String ipAddress) {
        Case caseEntity = getCaseById(id);
        caseRepository.delete(id);
        
        auditService.logAction("Case", id, "DELETE", deleterUsername, deleterId,
            "Deleted case: " + caseEntity.getCaseNumber(), ipAddress);
    }
}
