package com.example.doan_code_javaweb.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void send(String to, String email);
}
