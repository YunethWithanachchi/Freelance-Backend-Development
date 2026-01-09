package com.example.demo.repository;
import com.example.demo.model.Milestone;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {
    List<Milestone> findByContractId(String contractId);
}
