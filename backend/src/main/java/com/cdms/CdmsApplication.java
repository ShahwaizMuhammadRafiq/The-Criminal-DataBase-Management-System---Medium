package com.cdms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Application Class for Criminal Database Management System
 * 
 * Software Construction Features:
 * - @EnableAsync: Enables asynchronous method execution for multithreading
 * - Spring Boot auto-configuration handles thread pool management
 */
@SpringBootApplication
@EnableAsync
public class CdmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CdmsApplication.class, args);
    }
}
