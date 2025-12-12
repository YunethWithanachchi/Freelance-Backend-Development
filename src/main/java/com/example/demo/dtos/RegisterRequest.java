package com.example.demo.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private  String password;
    private String role; //CLIENT or FREELANCER
}
