package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.model.User;
import com.cdms.security.CustomUserDetails;
import com.cdms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Management Controller
 */
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(u -> u.setPassword(null)); // Don't expose passwords
        return ResponseEntity.ok(ApiResponse.success(users));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        user.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(
            @RequestBody User user,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        User created = userService.createUser(
            user,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        created.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success("User created successfully", created));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @RequestBody User user,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        User updated = userService.updateUser(
            id,
            user,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        updated.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        userService.deleteUser(
            id,
            userDetails.getUsername(),
            userDetails.getUser().getId(),
            request.getRemoteAddr()
        );
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}
