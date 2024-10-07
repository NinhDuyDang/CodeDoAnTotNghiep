package com.example.doan_code_javaweb.service;

import com.example.doan_code_javaweb.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void register(User user);
    String confirm(String token);
    Optional<User> findUserByEmail(String email);
    Integer updatePassword(String password, String email);
}
