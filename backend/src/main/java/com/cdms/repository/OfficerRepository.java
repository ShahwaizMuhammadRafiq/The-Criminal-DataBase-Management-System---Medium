package com.cdms.repository;

import com.cdms.model.Officer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Officer Repository
 */
@Repository
public class OfficerRepository extends GenericRepository<Officer> {
    
    public Optional<Officer> findByBadgeNumber(String badgeNumber) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(officer -> officer.getBadgeNumber().equals(badgeNumber))
                .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Officer> findByStatus(Officer.OfficerStatus status) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(officer -> officer.getStatus() == status)
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<Officer> findByDepartment(String department) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(officer -> officer.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
