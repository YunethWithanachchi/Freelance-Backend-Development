package com.example.demo.repository;

import com.example.demo.model.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ContractRepository extends MongoRepository<Contract, String> {
    List<Contract> findByClientId(String clientId);
    List<Contract> findByFreelancerId(String freelancerId);
}
