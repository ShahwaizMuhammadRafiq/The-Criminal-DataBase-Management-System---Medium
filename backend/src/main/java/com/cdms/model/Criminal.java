package com.cdms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Criminal {
    private Long id;
    private String name;
    private String alias;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String address;
    private String phone;
    private String photo;
    private List<String> identificationMarks;
    private String criminalHistory;
    private CriminalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
    
    public enum CriminalStatus { WANTED, ARRESTED, CONVICTED, RELEASED, UNKNOWN }
    
    public Criminal() {}
    
    public Criminal(Long id, String name, String alias, LocalDate dateOfBirth, String gender, String nationality, String address, String phone, String photo, List<String> identificationMarks, String criminalHistory, CriminalStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.id = id; this.name = name; this.alias = alias; this.dateOfBirth = dateOfBirth;
        this.gender = gender; this.nationality = nationality; this.address = address; this.phone = phone;
        this.photo = photo; this.identificationMarks = identificationMarks; this.criminalHistory = criminalHistory;
        this.status = status; this.createdAt = createdAt; this.updatedAt = updatedAt;
        this.createdBy = createdBy; this.updatedBy = updatedBy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public List<String> getIdentificationMarks() { return identificationMarks; }
    public void setIdentificationMarks(List<String> identificationMarks) { this.identificationMarks = identificationMarks; }
    public String getCriminalHistory() { return criminalHistory; }
    public void setCriminalHistory(String criminalHistory) { this.criminalHistory = criminalHistory; }
    public CriminalStatus getStatus() { return status; }
    public void setStatus(CriminalStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
}
