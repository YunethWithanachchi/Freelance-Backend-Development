package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("messages")
public class Message {
    @Id
    private String id;

    private String contractId; // messages are inside a contract
    private String senderId;
    private String receiverId;
    private String text;
    private String timestamp;
}
