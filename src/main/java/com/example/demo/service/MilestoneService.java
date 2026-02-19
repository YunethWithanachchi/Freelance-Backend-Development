package com.example.demo.service;
import com.example.demo.dto.MilestoneRequest;
import com.example.demo.dto.MilestoneResponse;
import com.example.demo.model.Milestone;
import com.example.demo.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public MilestoneResponse createMilestone(MilestoneRequest request) {
        Milestone milestone = new Milestone();
        milestone.setContractId(request.getContractId());
        milestone.setTitle(request.getTitle());
        milestone.setDescription(request.getDescription());
        milestone.setAmount(request.getAmount());
        milestone.setStatus("PENDING");
        milestoneRepository.save(milestone);
        return mapToResponse(milestone);
    }

    public List<MilestoneResponse> getMilestonesByContract(String contractId) {
        return milestoneRepository.findByContractId(contractId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MilestoneResponse updateMilestoneStatus(String milestoneId, String status) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new RuntimeException("Milestone not found"));
        milestone.setStatus(status);
        milestoneRepository.save(milestone);
        return mapToResponse(milestone);
    }

    private MilestoneResponse mapToResponse(Milestone milestone) {
        MilestoneResponse response = new MilestoneResponse();
        response.setId(milestone.getId());
        response.setContractId(milestone.getContractId());
        response.setTitle(milestone.getTitle());
        response.setDescription(milestone.getDescription());
        response.setAmount(milestone.getAmount());
        response.setStatus(milestone.getStatus());
        return response;
    }
}
