package com.example.demo.repository;
import com.example.demo.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByReceiverId(String receiverId);
    List<Review> findByJobId(String jobId);
}
