package com.example.demo.controllers;

import com.example.demo.entities.Job;
import com.example.demo.repositories.JobRepository;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobRepository jobRepository;
    private final JwtUtil jwtUtil;

    //Post a job (Client Only)
    @PostMapping
    public Job postJob(@RequestHeader("Authorization") String authHeader, @RequestBody Job jobRequest){
        String token = authHeader.substring(7);
        String clientId = jwtUtil.extractUserId(token);

        jobRequest.setClientId(clientId);
        jobRequest.setCreatedAt(Instant.now().toString());
        return jobRepository.save(jobRequest);
    }

    //Get all jobs
    @GetMapping
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }

    // Get jobs posted by a client
    @GetMapping("/my-jobs")
    public List<Job> getMyJobs(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        String clientId = jwtUtil.extractUserId(token);
        return jobRepository.findByClientId(clientId);
    }

    //Get job by id
    @GetMapping("/{id}")
    public Job getJob(@PathVariable String id){
        return jobRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }

    //Update job (Client only)
    @PutMapping("/{id}")
    public Job updateJob(@RequestHeader("Authorization") String authHeader,
                         @PathVariable String id,
                         @RequestBody Job jobRequest){
        String token = authHeader.substring(7);
        String clientId = jwtUtil.extractUserId(token);

        Job job = jobRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));

        if(!Objects.equals(job.getClientId(),clientId)){
            throw new RuntimeException("Not Authorized to edit this job");
        }

        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setBudget(jobRequest.getBudget());
        job.setCategory(jobRequest.getCategory());
        //Optionally update timestamp
        job.setCreatedAt(Instant.now().toString());

        return jobRepository.save(job);
    }

    //Delete job (Client only)
    @DeleteMapping("/{id}")
    public String deleteJob(@RequestHeader("Authorization") String authHeader,
                            @PathVariable String id){
        String token = authHeader.substring(7);
        String clientId = jwtUtil.extractUserId(token);

        Job job = jobRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));

        if (!Objects.equals(job.getClientId(),clientId)){
            throw new RuntimeException("Not authorized to delete this job");
        }
        jobRepository.delete(job);
        return "Job deleted successfully";
    }
}
