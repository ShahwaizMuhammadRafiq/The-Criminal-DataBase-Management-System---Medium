package com.cdms.config;

import com.cdms.model.*;
import com.cdms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Data Initializer
 * Loads sample data on application startup for testing
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OfficerRepository officerRepository;
    
    @Autowired
    private CriminalRepository criminalRepository;
    
    @Autowired
    private FIRRepository firRepository;
    
    @Autowired
    private CaseRepository caseRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        initializeUsers();
        initializeOfficers();
        initializeCriminals();
        initializeFIRs();
        initializeCases();
        
        System.out.println("========================================");
        System.out.println("CDMS Backend Started Successfully!");
        System.out.println("========================================");
        System.out.println("Sample Data Loaded:");
        System.out.println("- Admin User: admin / admin123");
        System.out.println("- Officer User: officer1 / officer123");
        System.out.println("- " + criminalRepository.count() + " Criminals");
        System.out.println("- " + firRepository.count() + " FIRs");
        System.out.println("- " + caseRepository.count() + " Cases");
        System.out.println("- " + officerRepository.count() + " Officers");
        System.out.println("========================================");
    }
    
    private void initializeUsers() {
        // Admin user
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEmail("admin@cdms.com");
        admin.setFullName("System Administrator");
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        userRepository.save(admin, 1L);
        
        // Officer user
        User officer = new User();
        officer.setId(2L);
        officer.setUsername("officer1");
        officer.setPassword(passwordEncoder.encode("officer123"));
        officer.setEmail("officer1@cdms.com");
        officer.setFullName("John Doe");
        officer.setRole(User.Role.OFFICER);
        officer.setActive(true);
        officer.setCreatedAt(LocalDateTime.now());
        officer.setUpdatedAt(LocalDateTime.now());
        userRepository.save(officer, 2L);
    }
    
    private void initializeOfficers() {
        Officer o1 = new Officer(1L, "B001", "Inspector Sharma", "Inspector", 
            "CID", "Central Police Station", "9876543210", "sharma@police.gov",
            Officer.OfficerStatus.ACTIVE, 0, 0, LocalDateTime.now().minusYears(5),
            LocalDateTime.now(), LocalDateTime.now());
        officerRepository.save(o1, 1L);
        
        Officer o2 = new Officer(2L, "B002", "SI Verma", "Sub-Inspector",
            "Crime Branch", "West Police Station", "9876543211", "verma@police.gov",
            Officer.OfficerStatus.ACTIVE, 0, 0, LocalDateTime.now().minusYears(3),
            LocalDateTime.now(), LocalDateTime.now());
        officerRepository.save(o2, 2L);
    }
    
    private void initializeCriminals() {
        Criminal c1 = new Criminal(1L, "Rahul Kumar", "Raju", 
            LocalDate.of(1985, 5, 15), "Male", "Indian",
            "123 Street, Delhi", "9999999999", null,
            Arrays.asList("Scar on left arm", "Tattoo on right shoulder"),
            "Multiple theft cases", Criminal.CriminalStatus.WANTED,
            LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        criminalRepository.save(c1, 1L);
        
        Criminal c2 = new Criminal(2L, "Priya Singh", "PS",
            LocalDate.of(1990, 8, 20), "Female", "Indian",
            "456 Avenue, Mumbai", "8888888888", null,
            Arrays.asList("Mole on chin"),
            "Fraud allegations", Criminal.CriminalStatus.ARRESTED,
            LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        criminalRepository.save(c2, 2L);
    }
    
    private void initializeFIRs() {
        FIR f1 = new FIR(1L, "FIR/2024/001", 1L, "Ramesh Gupta",
            "9777777777", "789 Road, Delhi", LocalDateTime.now().minusDays(2),
            "Central Market", "Theft of mobile phone and cash",
            "Theft", "IPC 379, 380", FIR.FIRStatus.UNDER_INVESTIGATION,
            1L, "Investigation in progress", LocalDateTime.now(),
            LocalDateTime.now(), 1L, 1L);
        firRepository.save(f1, 1L);
        
        FIR f2 = new FIR(2L, "FIR/2024/002", 2L, "Sunita Devi",
            "9666666666", "321 Lane, Mumbai", LocalDateTime.now().minusDays(5),
            "City Bank", "Online banking fraud",
            "Fraud", "IPC 420, IT Act 66C", FIR.FIRStatus.REGISTERED,
            null, null, LocalDateTime.now(),
            LocalDateTime.now(), 1L, 1L);
        firRepository.save(f2, 2L);
    }
    
    private void initializeCases() {
        Case case1 = new Case(1L, "CASE/2024/001", "Theft Case - Central Market",
            "Multiple instances of theft reported", Arrays.asList(1L),
            Arrays.asList(1L), 1L, LocalDate.now().minusDays(1),
            "District Court", "Judge Mehta", "Adv. Kumar", "Adv. Sharma",
            Case.CaseStatus.PENDING, LocalDate.now().plusDays(10),
            null, "Mobile phone recovered", "3 witnesses",
            LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        caseRepository.save(case1, 1L);
    }
}
