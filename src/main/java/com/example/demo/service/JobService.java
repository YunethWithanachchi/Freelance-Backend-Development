package com.example.demo.service;
import com.example.demo.dto.JobRequest;
import com.example.demo.dto.JobResponse;
import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobResponse createJob(JobRequest request, String clientId) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setBudgetMin(request.getBudgetMin());
        job.setBudgetMax(request.getBudgetMax());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setClientId(clientId);
        job.setStatus("OPEN");
        jobRepository.save(job);

        return mapToResponse(job);
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobResponse getJobById(String jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    public JobResponse updateJob(String jobId, JobRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setBudgetMin(request.getBudgetMin());
        job.setBudgetMax(request.getBudgetMax());
        job.setRequiredSkills(request.getRequiredSkills());
        jobRepository.save(job);
        return mapToResponse(job);
    }

    public void deleteJob(String jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        jobRepository.delete(job);
    }

    private JobResponse mapToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setBudgetMin(job.getBudgetMin());
        response.setBudgetMax(job.getBudgetMax());
        response.setRequiredSkills(job.getRequiredSkills());
        response.setClientId(job.getClientId());
        response.setStatus(job.getStatus());
        return response;
    }
}
