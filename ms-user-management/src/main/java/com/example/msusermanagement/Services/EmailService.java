package com.example.msusermanagement.Services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void send(SimpleMailMessage mail);
}
