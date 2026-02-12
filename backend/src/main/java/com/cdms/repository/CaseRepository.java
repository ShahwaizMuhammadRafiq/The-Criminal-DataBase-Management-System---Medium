package com.cdms.repository;

import com.cdms.model.Case;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Case Repository
 */
@Repository
public class CaseRepository extends GenericRepository<Case> {
    
    public Optional<Case> findByCaseNumber(String caseNumber) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(c -> c.getCaseNumber().equals(caseNumber))
                .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Case> findByOfficerId(Long officerId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(c -> c.getInvestigatingOfficerId() != null && 
                            c.getInvestigatingOfficerId().equals(officerId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Case> findByStatus(Case.CaseStatus status) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Case> findByCriminalId(Long criminalId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(c -> c.getCriminalIds() != null && 
                            c.getCriminalIds().contains(criminalId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
