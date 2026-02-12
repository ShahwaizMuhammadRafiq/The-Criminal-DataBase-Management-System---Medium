package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.Criminal;
import com.cdms.security.CustomUserDetails;
import com.cdms.service.CriminalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Criminal Controller
 * 
 * Each request runs in its own thread (Spring default)
 * Demonstrates concurrent API access
 */
@RestController
@RequestMapping("/criminals")
public class CriminalController {
    
    @Autowired
    private CriminalService criminalService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Criminal>>> getAllCriminals() {
        return ResponseEntity.ok(ApiResponse.success(criminalService.getAllCriminals()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Criminal>> getCriminalById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(criminalService.getCriminalById(id)));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Criminal>>> getCriminalsByStatus(@PathVariable Criminal.CriminalStatus status) {
        return ResponseEntity.ok(ApiResponse.success(criminalService.getCriminalsByStatus(status)));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Criminal>>> searchCriminals(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(criminalService.searchCriminalsByName(name)));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Criminal>> createCriminal(
            @RequestBody Criminal criminal,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Criminal created = criminalService.createCriminal(
            criminal,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Criminal created successfully", created));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Criminal>> updateCriminal(
            @PathVariable Long id,
            @RequestBody Criminal criminal,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Criminal updated = criminalService.updateCriminal(
            id,
            criminal,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Criminal updated successfully", updated));
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<Criminal>> updateStatus(
            @PathVariable Long id,
            @RequestParam Criminal.CriminalStatus status,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        Criminal updated = criminalService.updateStatus(
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
    public ResponseEntity<ApiResponse<Void>> deleteCriminal(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        criminalService.deleteCriminal(
            id,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("Criminal deleted successfully", null));
    }
}
