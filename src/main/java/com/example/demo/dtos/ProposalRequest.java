package com.example.demo.dtos;

import lombok.Data;

@Data
public class ProposalRequest {
    private String jobId;
    private Double amount;
    private String coverLetter;
}
