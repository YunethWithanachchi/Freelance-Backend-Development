package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contracts")
public class Contract {

    @Id
    private String id;

    private String jobId;
    private String clientId;
    private String freelancerId;
    private Double agreedAmount;
    // ACTIVE, COMPLETED, CANCELLED
    private String status;

    private String createdAt;
}