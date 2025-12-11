package com.example.demo.repository;
import com.example.demo.model.Proposal;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProposalRepository extends MongoRepository<Proposal, String> {
    List<Proposal> findByFreelancerId(String freelancerId);
    List<Proposal> findByJobId(String jobId);
}
