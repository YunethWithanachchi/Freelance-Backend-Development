package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContractRequest {

    @NotBlank(message = "Proposal ID is required")
    private String proposalId;

    // Optional: can set custom amount
    @NotNull(message = "Amount is required")
    private Double amount;

    // Getters & Setters
    public String getProposalId() { return proposalId; }
    public void setProposalId(String proposalId) { this.proposalId = proposalId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
