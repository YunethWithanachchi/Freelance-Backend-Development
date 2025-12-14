package com.example.demo.repositories;

import com.example.demo.entities.Proposal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProposalRepository extends MongoRepository<Proposal,String> {
    List<Proposal> findByJobId(String jobId);
    List<Proposal>findByFreelancerId(String freelancerId);

}
