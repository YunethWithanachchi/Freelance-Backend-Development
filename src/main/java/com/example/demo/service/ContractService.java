package com.example.demo.service;
import com.example.demo.dto.ContractRequest;
import com.example.demo.dto.ContractResponse;
import com.example.demo.model.Contract;
import com.example.demo.model.Proposal;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    public ContractResponse acceptProposal(ContractRequest request) {
        Proposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        Contract contract = new Contract();
        contract.setJobId(proposal.getJobId());
        contract.setClientId(proposal.getClientId());
        contract.setFreelancerId(proposal.getFreelancerId());
        contract.setProposalId(proposal.getId());
        contract.setAmount(request.getAmount());
        contract.setStatus("ACTIVE");
        contractRepository.save(contract);

        proposal.setStatus("ACCEPTED");
        proposalRepository.save(proposal);

        return mapToResponse(contract);
    }

    public List<ContractResponse> getContractsByClient(String clientId) {
        return contractRepository.findByClientId(clientId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ContractResponse> getContractsByFreelancer(String freelancerId) {
        return contractRepository.findByFreelancerId(freelancerId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ContractResponse mapToResponse(Contract contract) {
        ContractResponse response = new ContractResponse();
        response.setId(contract.getId());
        response.setJobId(contract.getJobId());
        response.setClientId(contract.getClientId());
        response.setFreelancerId(contract.getFreelancerId());
        response.setProposalId(contract.getProposalId());
        response.setAmount(contract.getAmount());
        response.setStatus(contract.getStatus());
        return response;
    }
}
