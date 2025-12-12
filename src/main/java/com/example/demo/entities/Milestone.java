package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "milestones")
public class Milestone {

    @Id
    private String id;

    private String contractId;
    private String title;
    private Double amount;

    //PENDING, IN_PROGRESS, COMPLETED
    private String status;
}
