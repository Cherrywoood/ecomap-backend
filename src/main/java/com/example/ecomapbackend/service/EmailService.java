package com.example.ecomapbackend.service;

public interface EmailService {
    void sendSimpleEmail(String fromMail, String toMail, String subject, String message);
}
