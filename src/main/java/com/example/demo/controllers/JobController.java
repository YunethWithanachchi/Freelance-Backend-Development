package com.example.demo.controllers;

import com.example.demo.entities.Job;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.entities.User;

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
    private final UserRepository userRepository;

    //Post a job (Client Only)
    @PostMapping
    public Job postJob(@RequestHeader("Authorization") String authHeader, @RequestBody Job jobRequest){
            String token = authHeader.substring(7);
            String email = jwtUtil.extractUserId(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!"CLIENT".equals(user.getRole())) {
                throw new RuntimeException("Only CLIENT users can post jobs");
            }

            jobRequest.setClientId(user.getId());
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
        String email = jwtUtil.extractUserId(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobRepository.findByClientId(user.getId());
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
                        @RequestBody Job jobRequest) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUserId(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"CLIENT".equals(user.getRole())) {
            throw new RuntimeException("Only CLIENT users can update jobs");
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getClientId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to edit this job");
        }

        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setBudget(jobRequest.getBudget());
        job.setCategory(jobRequest.getCategory());
        job.setCreatedAt(Instant.now().toString());

        return jobRepository.save(job);
    }


    //Delete job (Client only)
    @DeleteMapping("/{id}")
    public String deleteJob(@RequestHeader("Authorization") String authHeader,
                            @PathVariable String id) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUserId(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"CLIENT".equals(user.getRole())) {
            throw new RuntimeException("Only CLIENT users can delete jobs");
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getClientId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to delete this job");
        }

        jobRepository.delete(job);
        return "Job deleted successfully";
    }

}
