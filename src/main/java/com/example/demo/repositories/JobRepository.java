package com.example.demo.repositories;

import com.example.demo.entities.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job,String> {
    List<Job> findByClientId(String clientId);
}
