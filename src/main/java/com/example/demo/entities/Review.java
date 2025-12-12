package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private String jobId; // reference to the job that was reviewed
    private String reviewerId; // the user who wrote the review
    private String receiverId; // the user who receives the review

    private Integer rating; // 1-5
    private String comment;

}
