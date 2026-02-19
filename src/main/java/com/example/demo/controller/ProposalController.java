package com.example.demo.controller;
import com.example.demo.dto.ProposalRequest;
import com.example.demo.dto.ProposalResponse;
import com.example.demo.service.ProposalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalResponse> submitProposal(@Valid @RequestBody ProposalRequest request,
                                                           @RequestHeader("userId") String freelancerId) {
        return ResponseEntity.ok(proposalService.submitProposal(request, freelancerId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ProposalResponse>> getProposalsByJob(@PathVariable String jobId) {
        return ResponseEntity.ok(proposalService.getProposalsByJob(jobId));
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<ProposalResponse>> getProposalsByFreelancer(@PathVariable String freelancerId) {
        return ResponseEntity.ok(proposalService.getProposalsByFreelancer(freelancerId));
    }

    @DeleteMapping("/{proposalId}")
    public ResponseEntity<Void> withdrawProposal(@PathVariable String proposalId,
                                                 @RequestHeader("userId") String freelancerId) {
        proposalService.withdrawProposal(proposalId, freelancerId);
        return ResponseEntity.ok().build();
    }
}
