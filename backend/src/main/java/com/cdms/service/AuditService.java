package com.cdms.service;

import com.cdms.model.AuditLog;
import com.cdms.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Audit Service
 * 
 * Software Construction Features:
 * - @Async: Method runs in separate thread from thread pool
 * - Immutable AuditLog ensures thread safety
 * - Non-blocking audit logging for better performance
 */
@Service
public class AuditService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    /**
     * Asynchronous audit logging
     * Runs in background thread to avoid blocking main request
     * 
     * Thread Safety: AuditLog is immutable, safe to create in any thread
     */
    @Async
    public void logAction(String entityType, Long entityId, String action, 
                         String username, Long userId, String details, String ipAddress) {
        AuditLog log = AuditLog.create(entityType, entityId, action, username, userId, details, ipAddress);
        auditLogRepository.save(log, log.getId());
    }
    
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
    
    public List<AuditLog> getLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }
    
    public List<AuditLog> getLogsByEntityId(Long entityId) {
        return auditLogRepository.findByEntityId(entityId);
    }
    
    public List<AuditLog> getLogsByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }
    
    public List<AuditLog> getLogsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByDateRange(start, end);
    }
}
