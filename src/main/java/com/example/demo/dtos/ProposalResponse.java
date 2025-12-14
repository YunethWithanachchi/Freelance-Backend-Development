package com.example.demo.dtos;

import lombok.Data;

@Data
public class ProposalResponse {
    private String id;
    private String jobId;
    private String freelancerId;
    private Double amount;
    private String coverLetter;
    private String status;
    private String createdAt;
}
