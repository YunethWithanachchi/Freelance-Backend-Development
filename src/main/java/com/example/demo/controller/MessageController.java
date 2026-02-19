package com.example.demo.controller;
import com.example.demo.dto.MessageRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request,
                                                       @RequestHeader("userId") String senderId) {
        return ResponseEntity.ok(messageService.sendMessage(request, senderId));
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByContract(@PathVariable String contractId) {
        return ResponseEntity.ok(messageService.getMessagesByContract(contractId));
    }
}
