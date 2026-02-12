package com.cdms.repository;

import com.cdms.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository extending Generic Repository
 * Demonstrates specialization of generic repository
 */
@Repository
public class UserRepository extends GenericRepository<User> {
    
    /**
     * Find user by username
     * Thread-safe search using read lock from parent
     */
    public Optional<User> findByUsername(String username) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Check if username exists
     */
    public boolean existsByUsername(String username) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
        } finally {
            lock.readLock().unlock();
        }
    }
}
