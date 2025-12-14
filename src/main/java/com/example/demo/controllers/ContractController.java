package com.example.demo.controllers;

import com.example.demo.entities.Contract;
import com.example.demo.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractRepository contractRepository;

    @GetMapping("/client/{clientId}")
    public List<Contract> getClientContracts(@PathVariable String clientId){
        return contractRepository.findByClientId(clientId);
    }
    @GetMapping("/freelancer/{freelancerId}")
    public List<Contract> getFreelancerContracts(@PathVariable String freelancerId){
            return contractRepository.findByFreelancerId(freelancerId);
    }

    @GetMapping("/{id}")
    public Contract getContract(@PathVariable String id){
        return contractRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Contract not found"));
    }
}
