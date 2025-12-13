package com.example.demo.service;
import com.example.demo.dto.ProposalRequest;
import com.example.demo.dto.ProposalResponse;
import com.example.demo.model.Job;
import com.example.demo.model.Proposal;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private JobRepository jobRepository;

    public ProposalResponse submitProposal(ProposalRequest request, String freelancerId) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Proposal proposal = new Proposal();
        proposal.setJobId(job.getId());
        proposal.setFreelancerId(freelancerId);
        proposal.setCoverLetter(request.getCoverLetter());
        proposal.setAmount(request.getAmount());
        proposal.setEstimatedDays(request.getEstimatedDays());
        proposal.setStatus("PENDING");
        proposalRepository.save(proposal);

        return mapToResponse(proposal);
    }

    public List<ProposalResponse> getProposalsByJob(String jobId) {
        return proposalRepository.findByJobId(jobId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProposalResponse> getProposalsByFreelancer(String freelancerId) {
        return proposalRepository.findByFreelancerId(freelancerId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void withdrawProposal(String proposalId, String freelancerId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        if(!proposal.getFreelancerId().equals(freelancerId)) {
            throw new RuntimeException("Not authorized");
        }

        proposalRepository.delete(proposal);
    }

    private ProposalResponse mapToResponse(Proposal proposal) {
        ProposalResponse response = new ProposalResponse();
        response.setId(proposal.getId());
        response.setJobId(proposal.getJobId());
        response.setFreelancerId(proposal.getFreelancerId());
        response.setCoverLetter(proposal.getCoverLetter());
        response.setAmount(proposal.getAmount());
        response.setEstimatedDays(proposal.getEstimatedDays());
        response.setStatus(proposal.getStatus());
        return response;
    }
}
