package com.example.demo.service;
import com.example.demo.dto.ReviewRequest;
import com.example.demo.dto.ReviewResponse;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewResponse submitReview(ReviewRequest request, String reviewerId) {
        Review review = new Review();
        review.setJobId(request.getJobId());
        review.setReviewerId(reviewerId);
        review.setReceiverId(request.getReceiverId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(Instant.now());
        reviewRepository.save(review);
        return mapToResponse(review);
    }

    public List<ReviewResponse> getReviewsByReceiver(String receiverId) {
        return reviewRepository.findByReceiverId(receiverId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getReviewsByJob(String jobId) {
        return reviewRepository.findByJobId(jobId)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse mapToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setJobId(review.getJobId());
        response.setReviewerId(review.getReviewerId());
        response.setReceiverId(review.getReceiverId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }
}
