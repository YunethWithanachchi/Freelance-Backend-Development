package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;

public class MessageRequest {

    @NotBlank(message = "Contract ID is required")
    private String contractId;

    @NotBlank(message = "Receiver ID is required")
    private String receiverId;

    @NotBlank(message = "Message text is required")
    private String text;

    // Getters & Setters
    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
