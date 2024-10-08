package com.example.doan_code_javaweb.service.imp;

import com.example.doan_code_javaweb.entity.ConfirmationToken;
import com.example.doan_code_javaweb.repository.ConfirmTokenRepository;
import com.example.doan_code_javaweb.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenImp implements ConfirmationTokenService {
    @Autowired
    private ConfirmTokenRepository confirmTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return confirmTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
