package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProposalRequest {
//submit proposal 
    @NotBlank(message = "Job ID is required")
    private String jobId;

    @NotBlank(message = "Cover letter is required")
    private String coverLetter;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Estimated days is required")
    private Integer estimatedDays;

    // Getters & Setters
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Integer getEstimatedDays() { return estimatedDays; }
    public void setEstimatedDays(Integer estimatedDays) { this.estimatedDays = estimatedDays; }
}
