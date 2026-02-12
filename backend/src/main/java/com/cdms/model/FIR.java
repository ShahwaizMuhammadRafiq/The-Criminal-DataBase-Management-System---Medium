package com.cdms.model;

import java.time.LocalDateTime;

public class FIR {
    private Long id;
    private String firNumber;
    private Long criminalId;
    private String complainantName;
    private String complainantContact;
    private String complainantAddress;
    private LocalDateTime incidentDateTime;
    private String incidentLocation;
    private String incidentDescription;
    private String offenseType;
    private String sections;
    private FIRStatus status;
    private Long assignedOfficerId;
    private String investigationNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
    
    public enum FIRStatus { REGISTERED, UNDER_INVESTIGATION, CHARGE_SHEET_FILED, CLOSED, TRANSFERRED }
    
    public FIR() {}
    
    public FIR(Long id, String firNumber, Long criminalId, String complainantName, String complainantContact, String complainantAddress, LocalDateTime incidentDateTime, String incidentLocation, String incidentDescription, String offenseType, String sections, FIRStatus status, Long assignedOfficerId, String investigationNotes, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.id = id; this.firNumber = firNumber; this.criminalId = criminalId; this.complainantName = complainantName;
        this.complainantContact = complainantContact; this.complainantAddress = complainantAddress; this.incidentDateTime = incidentDateTime; this.incidentLocation = incidentLocation;
        this.incidentDescription = incidentDescription; this.offenseType = offenseType; this.sections = sections; this.status = status;
        this.assignedOfficerId = assignedOfficerId; this.investigationNotes = investigationNotes; this.createdAt = createdAt; this.updatedAt = updatedAt;
        this.createdBy = createdBy; this.updatedBy = updatedBy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirNumber() { return firNumber; }
    public void setFirNumber(String firNumber) { this.firNumber = firNumber; }
    public Long getCriminalId() { return criminalId; }
    public void setCriminalId(Long criminalId) { this.criminalId = criminalId; }
    public String getComplainantName() { return complainantName; }
    public void setComplainantName(String complainantName) { this.complainantName = complainantName; }
    public String getComplainantContact() { return complainantContact; }
    public void setComplainantContact(String complainantContact) { this.complainantContact = complainantContact; }
    public String getComplainantAddress() { return complainantAddress; }
    public void setComplainantAddress(String complainantAddress) { this.complainantAddress = complainantAddress; }
    public LocalDateTime getIncidentDateTime() { return incidentDateTime; }
    public void setIncidentDateTime(LocalDateTime incidentDateTime) { this.incidentDateTime = incidentDateTime; }
    public String getIncidentLocation() { return incidentLocation; }
    public void setIncidentLocation(String incidentLocation) { this.incidentLocation = incidentLocation; }
    public String getIncidentDescription() { return incidentDescription; }
    public void setIncidentDescription(String incidentDescription) { this.incidentDescription = incidentDescription; }
    public String getOffenseType() { return offenseType; }
    public void setOffenseType(String offenseType) { this.offenseType = offenseType; }
    public String getSections() { return sections; }
    public void setSections(String sections) { this.sections = sections; }
    public FIRStatus getStatus() { return status; }
    public void setStatus(FIRStatus status) { this.status = status; }
    public Long getAssignedOfficerId() { return assignedOfficerId; }
    public void setAssignedOfficerId(Long assignedOfficerId) { this.assignedOfficerId = assignedOfficerId; }
    public String getInvestigationNotes() { return investigationNotes; }
    public void setInvestigationNotes(String investigationNotes) { this.investigationNotes = investigationNotes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
}
