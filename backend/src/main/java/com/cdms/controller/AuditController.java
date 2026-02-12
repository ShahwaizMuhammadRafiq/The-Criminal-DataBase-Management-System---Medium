package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.AuditLog;
import com.cdms.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Audit Controller
 */
@RestController
@RequestMapping("/audit")
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {
    
    @Autowired
    private AuditService auditService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAllLogs() {
        return ResponseEntity.ok(ApiResponse.success(auditService.getAllLogs()));
    }
    
    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByEntityType(@PathVariable String entityType) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getLogsByEntityType(entityType)));
    }
    
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByEntityId(@PathVariable Long entityId) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getLogsByEntityId(entityId)));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getLogsByUserId(userId)));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(ApiResponse.success(auditService.getLogsByDateRange(start, end)));
    }
}
