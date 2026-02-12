package com.cdms.repository;

import com.cdms.model.AuditLog;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AuditLog Repository
 * Special handling for immutable entity
 */
@Repository
public class AuditLogRepository extends GenericRepository<AuditLog> {
    
    public List<AuditLog> findByEntityType(String entityType) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(log -> log.getEntityType().equals(entityType))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<AuditLog> findByEntityId(Long entityId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(log -> log.getEntityId().equals(entityId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<AuditLog> findByUserId(Long userId) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(log -> log.getUserId().equals(userId))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public List<AuditLog> findByDateRange(LocalDateTime start, LocalDateTime end) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(log -> !log.getTimestamp().isBefore(start) && 
                              !log.getTimestamp().isAfter(end))
                .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
