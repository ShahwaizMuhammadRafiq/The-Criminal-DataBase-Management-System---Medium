package com.cdms.service;

import com.cdms.model.Officer;
import com.cdms.repository.OfficerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Officer Service
 */
@Service
public class OfficerService {
    
    @Autowired
    private OfficerRepository officerRepository;
    
    @Autowired
    private AuditService auditService;
    
    private final Lock officerLock = new ReentrantLock(true);
    
    public Officer createOfficer(Officer officer, String creatorUsername, Long creatorId, String ipAddress) {
        officerLock.lock();
        try {
            officer.setCreatedAt(LocalDateTime.now());
            officer.setUpdatedAt(LocalDateTime.now());
            officer.setActiveCases(0);
            officer.setActiveFIRs(0);
            
            Long id = officerRepository.peekNextId();
            officer.setId(id);
            Officer saved = officerRepository.save(officer, id);
            
            auditService.logAction("Officer", id, "CREATE", creatorUsername, creatorId,
                "Created officer: " + officer.getName(), ipAddress);
            
            return saved;
        } finally {
            officerLock.unlock();
        }
    }
    
    public Officer getOfficerById(Long id) {
        return officerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Officer not found: " + id));
    }
    
    public Officer getOfficerByBadgeNumber(String badgeNumber) {
        return officerRepository.findByBadgeNumber(badgeNumber)
            .orElseThrow(() -> new IllegalArgumentException("Officer not found: " + badgeNumber));
    }
    
    public List<Officer> getAllOfficers() {
        return officerRepository.findAll();
    }
    
    public List<Officer> getOfficersByStatus(Officer.OfficerStatus status) {
        return officerRepository.findByStatus(status);
    }
    
    public List<Officer> getOfficersByDepartment(String department) {
        return officerRepository.findByDepartment(department);
    }
    
    public Officer updateOfficer(Long id, Officer officer, String updaterUsername, Long updaterId, String ipAddress) {
        officerLock.lock();
        try {
            Officer existing = getOfficerById(id);
            
            existing.setName(officer.getName());
            existing.setRank(officer.getRank());
            existing.setDepartment(officer.getDepartment());
            existing.setStation(officer.getStation());
            existing.setPhone(officer.getPhone());
            existing.setEmail(officer.getEmail());
            existing.setStatus(officer.getStatus());
            existing.setUpdatedAt(LocalDateTime.now());
            
            Officer updated = officerRepository.update(id, existing);
            
            auditService.logAction("Officer", id, "UPDATE", updaterUsername, updaterId,
                "Updated officer: " + existing.getName(), ipAddress);
            
            return updated;
        } finally {
            officerLock.unlock();
        }
    }
    
    public void deleteOfficer(Long id, String deleterUsername, Long deleterId, String ipAddress) {
        Officer officer = getOfficerById(id);
        officerRepository.delete(id);
        
        auditService.logAction("Officer", id, "DELETE", deleterUsername, deleterId,
            "Deleted officer: " + officer.getName(), ipAddress);
    }
}
