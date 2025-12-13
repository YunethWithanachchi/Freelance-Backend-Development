package com.example.demo.config;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SampleDataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepo,
            JobRepository jobRepo,
            ProposalRepository proposalRepo,
            ContractRepository contractRepo,
            MilestoneRepository milestoneRepo,
            MessageRepository messageRepo,
            ReviewRepository reviewRepo
    ) {
        return args -> {

            // ---- USERS ----
            User client = new User();
            client.setFullName("John Client");
            client.setEmail("client@example.com");
            client.setPassword("123456");
            client.setRole("CLIENT");
            userRepo.save(client);

            User freelancer = new User();
            freelancer.setFullName("Alex Freelancer");
            freelancer.setEmail("freelancer@example.com");
            freelancer.setPassword("123456");
            freelancer.setRole("FREELANCER");
            freelancer.setSkills(Arrays.asList("Java", "Spring Boot", "React"));
            userRepo.save(freelancer);


            // ---- JOBS ----
            Job job1 = new Job();
            job1.setTitle("Build website landing page");
            job1.setDescription("Need a responsive landing page for startup");
            job1.setBudgetMin(100);
            job1.setBudgetMax(250);
            job1.setRequiredSkills(Arrays.asList("HTML", "CSS", "JS"));
            job1.setClientId(client.getId());
            job1.setStatus("OPEN");
            jobRepo.save(job1);

            Job job2 = new Job();
            job2.setTitle("REST API for mobile app");
            job2.setDescription("Develop a backend REST API using Spring Boot");
            job2.setBudgetMin(200);
            job2.setBudgetMax(450);
            job2.setRequiredSkills(Arrays.asList("Java", "Spring Boot"));
            job2.setClientId(client.getId());
            job2.setStatus("OPEN");
            jobRepo.save(job2);


            // ---- PROPOSAL ----
            Proposal proposal = new Proposal();
            proposal.setJobId(job1.getId());
            proposal.setFreelancerId(freelancer.getId());
            proposal.setCoverLetter("I can build your landing page professionally.");
            proposal.setBidAmount(180);
            proposal.setStatus("PENDING");
            proposalRepo.save(proposal);


            // ---- CONTRACT ----
            Contract contract = new Contract();
            contract.setJobId(job1.getId());
            contract.setClientId(client.getId());
            contract.setFreelancerId(freelancer.getId());
            contract.setAmount(180);
            contract.setStatus("ACTIVE");
            contractRepo.save(contract);


            // ---- MILESTONES ----
            Milestone m1 = new Milestone();
            m1.setContractId(contract.getId());
            m1.setTitle("Initial Design");
            m1.setAmount(80);
            m1.setStatus("PENDING");
            milestoneRepo.save(m1);

            Milestone m2 = new Milestone();
            m2.setContractId(contract.getId());
            m2.setTitle("Final Delivery");
            m2.setAmount(100);
            m2.setStatus("PENDING");
            milestoneRepo.save(m2);


            // ---- MESSAGE ----
            Message msg = new Message();
            msg.setContractId(contract.getId());
            msg.setSenderId(client.getId());
            msg.setContent("Hi, let’s start today!");
            messageRepo.save(msg);


            // ---- REVIEW ----
            Review review = new Review();
            review.setJobId(job1.getId());
            review.setReviewerId(client.getId());
            review.setReceiverId(freelancer.getId());
            review.setRating(5);
            review.setComment("Excellent work!");
            reviewRepo.save(review);


            System.out.println("SAMPLE DATA INSERTED SUCCESSFULLY ✔");
        };
    }
}
