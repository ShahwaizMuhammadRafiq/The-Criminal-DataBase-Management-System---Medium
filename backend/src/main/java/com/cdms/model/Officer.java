package com.cdms.model;

import java.time.LocalDateTime;

public class Officer {
    private Long id;
    private String badgeNumber;
    private String name;
    private String rank;
    private String department;
    private String station;
    private String phone;
    private String email;
    private OfficerStatus status;
    private int activeCases;
    private int activeFIRs;
    private LocalDateTime joinedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum OfficerStatus { ACTIVE, ON_LEAVE, SUSPENDED, RETIRED }
    
    public Officer() {}
    
    public Officer(Long id, String badgeNumber, String name, String rank, String department, String station, String phone, String email, OfficerStatus status, int activeCases, int activeFIRs, LocalDateTime joinedDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id; this.badgeNumber = badgeNumber; this.name = name; this.rank = rank;
        this.department = department; this.station = station; this.phone = phone; this.email = email;
        this.status = status; this.activeCases = activeCases; this.activeFIRs = activeFIRs; this.joinedDate = joinedDate;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBadgeNumber() { return badgeNumber; }
    public void setBadgeNumber(String badgeNumber) { this.badgeNumber = badgeNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public OfficerStatus getStatus() { return status; }
    public void setStatus(OfficerStatus status) { this.status = status; }
    public int getActiveCases() { return activeCases; }
    public void setActiveCases(int activeCases) { this.activeCases = activeCases; }
    public int getActiveFIRs() { return activeFIRs; }
    public void setActiveFIRs(int activeFIRs) { this.activeFIRs = activeFIRs; }
    public LocalDateTime getJoinedDate() { return joinedDate; }
    public void setJoinedDate(LocalDateTime joinedDate) { this.joinedDate = joinedDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
