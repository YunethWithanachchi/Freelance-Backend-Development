package com.example.demo.service;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    public UserResponse updateProfile(String userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(updatedUser.getFullName());
        user.setBio(updatedUser.getBio());
        user.setLocation(updatedUser.getLocation());
        user.setAvatarUrl(updatedUser.getAvatarUrl());
        user.setSkills(updatedUser.getSkills());

        userRepository.save(user);
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setBio(user.getBio());
        response.setLocation(user.getLocation());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setSkills(user.getSkills());
        response.setAverageRating(user.getAverageRating());
        response.setRatingCount(user.getRatingCount());
        return response;
    }
}
