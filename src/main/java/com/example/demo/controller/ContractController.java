package com.example.demo.controller;
import com.example.demo.dto.ContractRequest;
import com.example.demo.dto.ContractResponse;
import com.example.demo.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/accept")
    public ResponseEntity<ContractResponse> acceptProposal(@Valid @RequestBody ContractRequest request) {
        return ResponseEntity.ok(contractService.acceptProposal(request));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContractResponse>> getContractsByClient(@PathVariable String clientId) {
        return ResponseEntity.ok(contractService.getContractsByClient(clientId));
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<ContractResponse>> getContractsByFreelancer(@PathVariable String freelancerId) {
        return ResponseEntity.ok(contractService.getContractsByFreelancer(freelancerId));
    }
}
