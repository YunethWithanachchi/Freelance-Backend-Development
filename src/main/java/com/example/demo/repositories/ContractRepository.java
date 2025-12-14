package com.example.demo.repositories;

import com.example.demo.entities.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContractRepository extends MongoRepository<Contract,String> {
    List<Contract>findByClientId(String clientId);
    List<Contract>findByFreelancerId(String freelancerId);
    boolean existsByProposalId(String proposalId);
}
