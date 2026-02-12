package com.cdms.repository;

import com.cdms.model.Criminal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Criminal Repository
 */
@Repository
public class CriminalRepository extends GenericRepository<Criminal> {
    
    public List<Criminal> findByStatus(Criminal.CriminalStatus status) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(criminal -> criminal.getStatus() == status)
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Criminal> searchByName(String name) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(criminal -> criminal.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
