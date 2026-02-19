package com.example.demo.controller;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> submitReview(@Valid @RequestBody ReviewRequest request,
                                                       @RequestHeader("userId") String reviewerId) {
        return ResponseEntity.ok(reviewService.submitReview(request, reviewerId));
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByReceiver(@PathVariable String receiverId) {
        return ResponseEntity.ok(reviewService.getReviewsByReceiver(receiverId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByJob(@PathVariable String jobId) {
        return ResponseEntity.ok(reviewService.getReviewsByJob(jobId));
    }
}
