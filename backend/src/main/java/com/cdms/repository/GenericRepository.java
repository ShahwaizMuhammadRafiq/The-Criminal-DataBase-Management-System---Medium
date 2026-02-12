package com.cdms.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generic Thread-Safe In-Memory Repository
 * 
 * Software Construction & Design Patterns:
 * 
 * 1. GENERICS: Repository<T> allows reuse for any entity type
 * 
 * 2. CONCURRENCY & THREAD-SAFETY:
 *    - ConcurrentHashMap: Thread-safe storage for O(1) access
 *    - AtomicLong: Thread-safe ID generation
 *    - ReadWriteLock: Allows multiple concurrent reads, exclusive writes
 *    - Prevents race conditions in multi-threaded environment
 * 
 * 3. SYNCHRONIZATION:
 *    - Read lock: Multiple threads can read simultaneously
 *    - Write lock: Exclusive access for modifications
 *    - Deadlock prevention: Consistent lock ordering
 * 
 * 4. REPOSITORY PATTERN:
 *    - Abstracts data storage from business logic
 *    - Easy to swap in-memory storage with database later
 */
public class GenericRepository<T> {
    
    // Thread-safe storage using ConcurrentHashMap
    protected final Map<Long, T> storage = new ConcurrentHashMap<>();
    
    // Thread-safe ID generator using AtomicLong
    protected final AtomicLong idGenerator = new AtomicLong(1);
    
    // ReadWriteLock for fine-grained concurrency control
    // Multiple threads can hold read lock, but only one can hold write lock
    protected final ReadWriteLock lock = new ReentrantReadWriteLock(true); // fair lock
    
    /**
     * Save entity with write lock
     * Write lock ensures exclusive access during modification
     */
    public T save(T entity, Long id) {
        lock.writeLock().lock();
        try {
            if (id == null) {
                id = idGenerator.getAndIncrement();
            }
            storage.put(id, entity);
            return entity;
        } finally {
            lock.writeLock().unlock(); // Always release lock
        }
    }
    
    /**
     * Find by ID with read lock
     * Read lock allows multiple concurrent reads
     */
    public Optional<T> findById(Long id) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(storage.get(id));
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Find all entities with read lock
     * CopyOnWriteArrayList ensures thread-safe iteration
     */
    public List<T> findAll() {
        lock.readLock().lock();
        try {
            return new CopyOnWriteArrayList<>(storage.values());
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Find with predicate (filtering)
     * Read lock for thread-safe iteration
     */
    public List<T> findBy(Predicate<T> predicate) {
        lock.readLock().lock();
        try {
            return storage.values().stream()
                .filter(predicate)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Update entity with write lock
     */
    public T update(Long id, T entity) {
        lock.writeLock().lock();
        try {
            if (!storage.containsKey(id)) {
                throw new IllegalArgumentException("Entity not found with id: " + id);
            }
            storage.put(id, entity);
            return entity;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Delete entity with write lock
     */
    public boolean delete(Long id) {
        lock.writeLock().lock();
        try {
            return storage.remove(id) != null;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    /**
     * Count entities with read lock
     */
    public long count() {
        lock.readLock().lock();
        try {
            return storage.size();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Check existence with read lock
     */
    public boolean exists(Long id) {
        lock.readLock().lock();
        try {
            return storage.containsKey(id);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    /**
     * Get next ID without incrementing
     */
    public Long peekNextId() {
        return idGenerator.get();
    }
    
    /**
     * Clear all data - useful for testing
     * Write lock for exclusive access
     */
    public void clear() {
        lock.writeLock().lock();
        try {
            storage.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
