package com.cdms.model;

import java.time.LocalDateTime;

/**
 * IMMUTABLE Entity: AuditLog
 */
public class AuditLog {
    private final Long id;
    private final String entityType;
    private final Long entityId;
    private final String action;
    private final String username;
    private final Long userId;
    private final String details;
    private final String ipAddress;
    private final LocalDateTime timestamp;
    
    public AuditLog() {
        this.id = null; this.entityType = null; this.entityId = null; this.action = null;
        this.username = null; this.userId = null; this.details = null; this.ipAddress = null;
        this.timestamp = null;
    }
    
    public AuditLog(Long id, String entityType, Long entityId, String action, String username, Long userId, String details, String ipAddress, LocalDateTime timestamp) {
        this.id = id; this.entityType = entityType; this.entityId = entityId; this.action = action;
        this.username = username; this.userId = userId; this.details = details; this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }
    
    public static AuditLog create(String entityType, Long entityId, String action, String username, Long userId, String details, String ipAddress) {
        return new AuditLog(System.currentTimeMillis(), entityType, entityId, action, username, userId, details, ipAddress, LocalDateTime.now());
    }

    public Long getId() { return id; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public String getAction() { return action; }
    public String getUsername() { return username; }
    public Long getUserId() { return userId; }
    public String getDetails() { return details; }
    public String getIpAddress() { return ipAddress; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
