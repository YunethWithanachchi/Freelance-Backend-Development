package com.example.demo.controllers;

import com.example.demo.dtos.ProposalRequest;
import com.example.demo.dtos.ProposalResponse;
import com.example.demo.entities.Contract;
import com.example.demo.entities.Job;
import com.example.demo.entities.Proposal;
import com.example.demo.repositories.ContractRepository;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.ProposalRepository;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalRepository proposalRepository;
    private final JobRepository jobRepository;
    private final ContractRepository contractRepository;
    private final JwtUtil jwtUtil;

    private ProposalResponse mapToResponse(Proposal proposal){
        ProposalResponse response = new ProposalResponse();
        response.setId(proposal.getId());
        response.setJobId(proposal.getJobId());
        response.setFreelancerId(proposal.getFreelancerId());
        response.setAmount(proposal.getAmount());
        response.setCoverLetter(proposal.getCoverLetter());
        response.setStatus(proposal.getStatus());
        response.setCreatedAt(proposal.getCreatedAt());

        return response;
    }

    @PostMapping
    public ProposalResponse submitProposal(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody ProposalRequest request){
        String token = authHeader.substring(7);
        String freelancerId = jwtUtil.extractUserId(token);

        Proposal proposal = new Proposal();
        proposal.setJobId(request.getJobId());
        proposal.setFreelancerId(freelancerId);
        proposal.setAmount(request.getAmount());
        proposal.setCoverLetter(request.getCoverLetter());
        proposal.setStatus("PENDING");
        proposal.setCreatedAt(Instant.now().toString());

        return mapToResponse(proposalRepository.save(proposal));
    }

    @GetMapping("/job/{jobId}")
    public List<ProposalResponse> getProposalsForJob(@PathVariable String jobId){
        return proposalRepository.findByJobId(jobId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/my-proposals")
    public List<ProposalResponse> getMyProposals(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String freelancerId = jwtUtil.extractUserId(token);

        return proposalRepository.findByFreelancerId(freelancerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/status")
    public ProposalResponse updateProposalStatus(@PathVariable String id,@RequestParam String status){
        Proposal proposal = proposalRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Proposal not found"));
        if (!status.equals("ACCEPTED") && !status.equals("REJECTED")){
            throw new RuntimeException("Invalid status");
        }
        proposal.setStatus(status);
        return mapToResponse(proposalRepository.save(proposal));
    }

    @PostMapping("/{proposalId}/accept")
    public Contract acceptProposal(@PathVariable String proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        if ("ACCEPTED".equals(proposal.getStatus())) {
            throw new RuntimeException(("Proposal already accepted"));
        }

        proposal.setStatus("ACCEPTED");
        proposalRepository.save(proposal);

        Job job = jobRepository.findById(proposal.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        //prevent duplicate contracts
        if (contractRepository.existsByProposalId(proposalId)) {
            throw new RuntimeException("Contract already exists");
        }
        Contract contract = new Contract();
        contract.setJobId(job.getId());
        contract.setClientId(job.getClientId());
        contract.setFreelancerId(proposal.getFreelancerId());
        contract.setProposalId(proposal.getId());
        contract.setAgreedAmount(proposal.getAmount());
        contract.setStatus("ACTIVE");

        return contractRepository.save(contract);
    }

}
