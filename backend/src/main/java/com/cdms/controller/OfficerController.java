package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.Officer;
import com.cdms.security.CustomUserDetails;
import com.cdms.service.OfficerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Officer Controller
 */
@RestController
@RequestMapping("/officers")
public class OfficerController {
    
    @Autowired
    private OfficerService officerService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Officer>>> getAllOfficers() {
        return ResponseEntity.ok(ApiResponse.success(officerService.getAllOfficers()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Officer>> getOfficerById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(officerService.getOfficerById(id)));
    }
    
    @GetMapping("/badge/{badgeNumber}")
    public ResponseEntity<ApiResponse<Officer>> getOfficerByBadgeNumber(@PathVariable String badgeNumber) {
        return ResponseEntity.ok(ApiResponse.success(officerService.getOfficerByBadgeNumber(badgeNumber)));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Officer>>> getOfficersByStatus(@PathVariable Officer.OfficerStatus status) {
        return ResponseEntity.ok(ApiResponse.success(officerService.getOfficersByStatus(status)));
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<Officer>>> getOfficersByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(ApiResponse.success(officerService.getOfficersByDepartment(department)));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Officer>> createOfficer(
            @RequestBody Officer officer,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Officer created = officerService.createOfficer(
            officer,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Officer created successfully", created));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Officer>> updateOfficer(
            @PathVariable Long id,
            @RequestBody Officer officer,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Officer updated = officerService.updateOfficer(
            id,
            officer,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Officer updated successfully", updated));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteOfficer(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        officerService.deleteOfficer(
            id,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Officer deleted successfully", null));
    }
}
