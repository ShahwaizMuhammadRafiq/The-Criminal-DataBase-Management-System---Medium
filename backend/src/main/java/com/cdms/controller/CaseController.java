package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.Case;
import com.cdms.security.CustomUserDetails;
import com.cdms.service.CaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Case Controller
 */
@RestController
@RequestMapping("/cases")
public class CaseController {
    
    @Autowired
    private CaseService caseService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Case>>> getAllCases() {
        return ResponseEntity.ok(ApiResponse.success(caseService.getAllCases()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Case>> getCaseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(caseService.getCaseById(id)));
    }
    
    @GetMapping("/number/{caseNumber}")
    public ResponseEntity<ApiResponse<Case>> getCaseByCaseNumber(@PathVariable String caseNumber) {
        return ResponseEntity.ok(ApiResponse.success(caseService.getCaseByCaseNumber(caseNumber)));
    }
    
    @GetMapping("/officer/{officerId}")
    public ResponseEntity<ApiResponse<List<Case>>> getCasesByOfficerId(@PathVariable Long officerId) {
        return ResponseEntity.ok(ApiResponse.success(caseService.getCasesByOfficerId(officerId)));
    }
    
    @GetMapping("/criminal/{criminalId}")
    public ResponseEntity<ApiResponse<List<Case>>> getCasesByCriminalId(@PathVariable Long criminalId) {
        return ResponseEntity.ok(ApiResponse.success(caseService.getCasesByCriminalId(criminalId)));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Case>>> getCasesByStatus(@PathVariable Case.CaseStatus status) {
        return ResponseEntity.ok(ApiResponse.success(caseService.getCasesByStatus(status)));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Case>> createCase(
            @RequestBody Case caseEntity,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Case created = caseService.createCase(
            caseEntity,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Case created successfully", created));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Case>> updateCase(
            @PathVariable Long id,
            @RequestBody Case caseEntity,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Case updated = caseService.updateCase(
            id,
            caseEntity,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Case updated successfully", updated));
    }
    
    @PatchMapping("/{id}/assign/{officerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Case>> assignToOfficer(
            @PathVariable Long id,
            @PathVariable Long officerId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Case updated = caseService.assignToOfficer(
            id,
            officerId,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Case assigned successfully", updated));
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Case>> updateStatus(
            @PathVariable Long id,
            @RequestParam Case.CaseStatus status,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Case updated = caseService.updateStatus(
            id,
            status,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", updated));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCase(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        caseService.deleteCase(
            id,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Case deleted successfully", null));
    }
}
