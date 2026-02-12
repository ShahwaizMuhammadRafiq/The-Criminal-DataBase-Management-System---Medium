package com.cdms.service;

import com.cdms.model.User;
import com.cdms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User Service
 * 
 * Software Construction Features:
 * - Synchronized user creation to prevent duplicate usernames
 * - ReentrantLock for deadlock-safe operations
 * - Thread-safe password encoding
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuditService auditService;
    
    // Explicit lock for user creation to prevent race conditions
    private final Lock userCreationLock = new ReentrantLock(true); // fair lock
    
    /**
     * Create user with explicit locking
     * Prevents duplicate username creation in concurrent environment
     */
    public User createUser(User user, String creatorUsername, Long creatorId, String ipAddress) {
        userCreationLock.lock();
        try {
            // Check if username exists
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + user.getUsername());
            }
            
            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setActive(true);
            
            Long id = userRepository.peekNextId();
            user.setId(id);
            User saved = userRepository.save(user, id);
            
            // Async audit logging
            auditService.logAction("User", id, "CREATE", creatorUsername, creatorId, 
                "Created user: " + user.getUsername(), ipAddress);
            
            return saved;
        } finally {
            userCreationLock.unlock();
        }
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long id, User user, String updaterUsername, Long updaterId, String ipAddress) {
        User existing = getUserById(id);
        
        existing.setEmail(user.getEmail());
        existing.setFullName(user.getFullName());
        existing.setRole(user.getRole());
        existing.setActive(user.isActive());
        existing.setUpdatedAt(LocalDateTime.now());
        
        User updated = userRepository.update(id, existing);
        
        auditService.logAction("User", id, "UPDATE", updaterUsername, updaterId,
            "Updated user: " + existing.getUsername(), ipAddress);
        
        return updated;
    }
    
    public void deleteUser(Long id, String deleterUsername, Long deleterId, String ipAddress) {
        User user = getUserById(id);
        userRepository.delete(id);
        
        auditService.logAction("User", id, "DELETE", deleterUsername, deleterId,
            "Deleted user: " + user.getUsername(), ipAddress);
    }
}
