package com.cdms.service;

import com.cdms.model.Criminal;
import com.cdms.repository.CriminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Criminal Service
 * 
 * Software Construction Features:
 * - Thread-safe CRUD operations
 * - Synchronized status updates
 * - Deadlock-safe locking strategy
 */
@Service
public class CriminalService {
    
    @Autowired
    private CriminalRepository criminalRepository;
    
    @Autowired
    private AuditService auditService;
    
    // Locks for different operations to prevent deadlocks
    private final Lock createLock = new ReentrantLock(true);
    private final Lock updateLock = new ReentrantLock(true);
    
    public Criminal createCriminal(Criminal criminal, String creatorUsername, Long creatorId, String ipAddress) {
        createLock.lock();
        try {
            criminal.setCreatedAt(LocalDateTime.now());
            criminal.setUpdatedAt(LocalDateTime.now());
            criminal.setCreatedBy(creatorId);
            criminal.setUpdatedBy(creatorId);
            
            Long id = criminalRepository.peekNextId();
            criminal.setId(id);
            Criminal saved = criminalRepository.save(criminal, id);
            
            auditService.logAction("Criminal", id, "CREATE", creatorUsername, creatorId,
                "Created criminal: " + criminal.getName(), ipAddress);
            
            return saved;
        } finally {
            createLock.unlock();
        }
    }
    
    public Criminal getCriminalById(Long id) {
        return criminalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Criminal not found: " + id));
    }
    
    public List<Criminal> getAllCriminals() {
        return criminalRepository.findAll();
    }
    
    public List<Criminal> getCriminalsByStatus(Criminal.CriminalStatus status) {
        return criminalRepository.findByStatus(status);
    }
    
    public List<Criminal> searchCriminalsByName(String name) {
        return criminalRepository.searchByName(name);
    }
    
    /**
     * Synchronized update to prevent race conditions
     */
    public Criminal updateCriminal(Long id, Criminal criminal, String updaterUsername, Long updaterId, String ipAddress) {
        updateLock.lock();
        try {
            Criminal existing = getCriminalById(id);
            
            existing.setName(criminal.getName());
            existing.setAlias(criminal.getAlias());
            existing.setDateOfBirth(criminal.getDateOfBirth());
            existing.setGender(criminal.getGender());
            existing.setNationality(criminal.getNationality());
            existing.setAddress(criminal.getAddress());
            existing.setPhone(criminal.getPhone());
            existing.setPhoto(criminal.getPhoto());
            existing.setIdentificationMarks(criminal.getIdentificationMarks());
            existing.setCriminalHistory(criminal.getCriminalHistory());
            existing.setStatus(criminal.getStatus());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy(updaterId);
            
            Criminal updated = criminalRepository.update(id, existing);
            
            auditService.logAction("Criminal", id, "UPDATE", updaterUsername, updaterId,
                "Updated criminal: " + existing.getName(), ipAddress);
            
            return updated;
        } finally {
            updateLock.unlock();
        }
    }
    
    /**
     * Thread-safe status update
     */
    public Criminal updateStatus(Long id, Criminal.CriminalStatus status, String updaterUsername, Long updaterId, String ipAddress) {
        updateLock.lock();
        try {
            Criminal criminal = getCriminalById(id);
            criminal.setStatus(status);
            criminal.setUpdatedAt(LocalDateTime.now());
            criminal.setUpdatedBy(updaterId);
            
            Criminal updated = criminalRepository.update(id, criminal);
            
            auditService.logAction("Criminal", id, "STATUS_UPDATE", updaterUsername, updaterId,
                "Changed status to: " + status, ipAddress);
            
            return updated;
        } finally {
            updateLock.unlock();
        }
    }
    
    public void deleteCriminal(Long id, String deleterUsername, Long deleterId, String ipAddress) {
        Criminal criminal = getCriminalById(id);
        criminalRepository.delete(id);
        
        auditService.logAction("Criminal", id, "DELETE", deleterUsername, deleterId,
            "Deleted criminal: " + criminal.getName(), ipAddress);
    }
}
