package com.example.demo.service;
import com.example.demo.dto.MessageRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageResponse sendMessage(MessageRequest request, String senderId) {
        Message message = new Message();
        message.setContractId(request.getContractId());
        message.setSenderId(senderId);
        message.setReceiverId(request.getReceiverId());
        message.setText(request.getText());
        message.setRead(false);
        message.setCreatedAt(Instant.now());
        messageRepository.save(message);
        return mapToResponse(message);
    }

    public List<MessageResponse> getMessagesByContract(String contractId) {
        return messageRepository.findByContractIdOrderByTimestampAsc(contractId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MessageResponse mapToResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setContractId(message.getContractId());
        response.setSenderId(message.getSenderId());
        response.setReceiverId(message.getReceiverId());
        response.setText(message.getText());
        response.setRead(message.isRead());
        response.setCreatedAt(message.getCreatedAt());
        return response;
    }
}
