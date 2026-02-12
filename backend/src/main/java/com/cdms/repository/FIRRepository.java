package com.cdms.repository;

import com.cdms.model.FIR;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FIR Repository
 */
@Repository
public class FIRRepository extends GenericRepository<FIR> {
    
    public Optional<FIR> findByFirNumber(String firNumber) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(fir -> fir.getFirNumber().equals(firNumber))
                .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<FIR> findByCriminalId(Long criminalId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(fir -> fir.getCriminalId().equals(criminalId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<FIR> findByOfficerId(Long officerId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(fir -> fir.getAssignedOfficerId() != null && 
                              fir.getAssignedOfficerId().equals(officerId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<FIR> findByStatus(FIR.FIRStatus status) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(fir -> fir.getStatus() == status)
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
