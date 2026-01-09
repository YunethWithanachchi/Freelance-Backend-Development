package com.example.demo.repository;
import com.example.demo.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByClientId(String clientId);
}
