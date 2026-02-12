package com.cdms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Case {
    private Long id;
    private String caseNumber;
    private String title;
    private String description;
    private List<Long> criminalIds;
    private List<Long> firIds;
    private Long investigatingOfficerId;
    private LocalDate filingDate;
    private String court;
    private String judge;
    private String prosecutorName;
    private String defenseAdvocate;
    private CaseStatus status;
    private LocalDate hearingDate;
    private String verdict;
    private String evidenceList;
    private String witnessDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
    private Long updatedBy;
    
    public enum CaseStatus { FILED, PENDING, HEARING, VERDICT_ANNOUNCED, CLOSED, APPEALED }
    
    public Case() {}
    
    public Case(Long id, String caseNumber, String title, String description, List<Long> criminalIds, List<Long> firIds, Long investigatingOfficerId, LocalDate filingDate, String court, String judge, String prosecutorName, String defenseAdvocate, CaseStatus status, LocalDate hearingDate, String verdict, String evidenceList, String witnessDetails, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy) {
        this.id = id; this.caseNumber = caseNumber; this.title = title; this.description = description;
        this.criminalIds = criminalIds; this.firIds = firIds; this.investigatingOfficerId = investigatingOfficerId;
        this.filingDate = filingDate; this.court = court; this.judge = judge; this.prosecutorName = prosecutorName;
        this.defenseAdvocate = defenseAdvocate; this.status = status; this.hearingDate = hearingDate; this.verdict = verdict;
        this.evidenceList = evidenceList; this.witnessDetails = witnessDetails; this.createdAt = createdAt; this.updatedAt = updatedAt;
        this.createdBy = createdBy; this.updatedBy = updatedBy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCaseNumber() { return caseNumber; }
    public void setCaseNumber(String caseNumber) { this.caseNumber = caseNumber; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Long> getCriminalIds() { return criminalIds; }
    public void setCriminalIds(List<Long> criminalIds) { this.criminalIds = criminalIds; }
    public List<Long> getFirIds() { return firIds; }
    public void setFirIds(List<Long> firIds) { this.firIds = firIds; }
    public Long getInvestigatingOfficerId() { return investigatingOfficerId; }
    public void setInvestigatingOfficerId(Long investigatingOfficerId) { this.investigatingOfficerId = investigatingOfficerId; }
    public LocalDate getFilingDate() { return filingDate; }
    public void setFilingDate(LocalDate filingDate) { this.filingDate = filingDate; }
    public String getCourt() { return court; }
    public void setCourt(String court) { this.court = court; }
    public String getJudge() { return judge; }
    public void setJudge(String judge) { this.judge = judge; }
    public String getProsecutorName() { return prosecutorName; }
    public void setProsecutorName(String prosecutorName) { this.prosecutorName = prosecutorName; }
    public String getDefenseAdvocate() { return defenseAdvocate; }
    public void setDefenseAdvocate(String defenseAdvocate) { this.defenseAdvocate = defenseAdvocate; }
    public CaseStatus getStatus() { return status; }
    public void setStatus(CaseStatus status) { this.status = status; }
    public LocalDate getHearingDate() { return hearingDate; }
    public void setHearingDate(LocalDate hearingDate) { this.hearingDate = hearingDate; }
    public String getVerdict() { return verdict; }
    public void setVerdict(String verdict) { this.verdict = verdict; }
    public String getEvidenceList() { return evidenceList; }
    public void setEvidenceList(String evidenceList) { this.evidenceList = evidenceList; }
    public String getWitnessDetails() { return witnessDetails; }
    public void setWitnessDetails(String witnessDetails) { this.witnessDetails = witnessDetails; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
}
