package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.FIR;
import com.cdms.security.CustomUserDetails;
import com.cdms.service.FIRService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FIR Controller
 */
@RestController
@RequestMapping("/firs")
public class FIRController {
    
    @Autowired
    private FIRService firService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<FIR>>> getAllFIRs() {
        return ResponseEntity.ok(ApiResponse.success(firService.getAllFIRs()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FIR>> getFIRById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(firService.getFIRById(id)));
    }
    
    @GetMapping("/number/{firNumber}")
    public ResponseEntity<ApiResponse<FIR>> getFIRByNumber(@PathVariable String firNumber) {
        return ResponseEntity.ok(ApiResponse.success(firService.getFIRByNumber(firNumber)));
    }
    
    @GetMapping("/criminal/{criminalId}")
    public ResponseEntity<ApiResponse<List<FIR>>> getFIRsByCriminalId(@PathVariable Long criminalId) {
        return ResponseEntity.ok(ApiResponse.success(firService.getFIRsByCriminalId(criminalId)));
    }
    
    @GetMapping("/officer/{officerId}")
    public ResponseEntity<ApiResponse<List<FIR>>> getFIRsByOfficerId(@PathVariable Long officerId) {
        return ResponseEntity.ok(ApiResponse.success(firService.getFIRsByOfficerId(officerId)));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<FIR>>> getFIRsByStatus(@PathVariable FIR.FIRStatus status) {
        return ResponseEntity.ok(ApiResponse.success(firService.getFIRsByStatus(status)));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<FIR>> createFIR(
            @RequestBody FIR fir,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        FIR created = firService.createFIR(
            fir,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("FIR created successfully", created));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<FIR>> updateFIR(
            @PathVariable Long id,
            @RequestBody FIR fir,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        FIR updated = firService.updateFIR(
            id,
            fir,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("FIR updated successfully", updated));
    }
    
    @PatchMapping("/{id}/assign/{officerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FIR>> assignToOfficer(
            @PathVariable Long id,
            @PathVariable Long officerId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        FIR updated = firService.assignToOfficer(
            id,
            officerId,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("FIR assigned successfully", updated));
    }
    
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<ApiResponse<FIR>> updateStatus(
            @PathVariable Long id,
            @RequestParam FIR.FIRStatus status,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        FIR updated = firService.updateStatus(
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
    public ResponseEntity<ApiResponse<Void>> deleteFIR(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        firService.deleteFIR(
            id,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("FIR deleted successfully", null));
    }
}
