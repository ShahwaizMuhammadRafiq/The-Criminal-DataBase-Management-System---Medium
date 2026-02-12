package com.cdms.controller;

import com.cdms.dto.ApiResponse;
import com.cdms.dto.AuthRequest;
import com.cdms.dto.AuthResponse;
import com.cdms.model.User;
import com.cdms.security.JwtUtil;
import com.cdms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * Handles user login and registration
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        
        // Generate token
        String token = jwtUtil.generateToken(userDetails);
        
        // Get user info
        User user = userService.getUserByUsername(request.getUsername());
        
        AuthResponse response = new AuthResponse(
            token,
            user.getUsername(),
            user.getRole().name(),
            "Login successful"
        );
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user, HttpServletRequest request) {
        User created = userService.createUser(user, "system", 0L, request.getRemoteAddr());
        created.setPassword(null); // Don't return password
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", created));
    }
}
