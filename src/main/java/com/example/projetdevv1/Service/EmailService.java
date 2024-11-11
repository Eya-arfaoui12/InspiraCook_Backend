package com.example.projetdevv1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account Verification");
        message.setText("To verify your account, please click here: "
                + "http://localhost:8082/api/auth/verify?token=" + token); // Replace with your frontend URL

        javaMailSender.send(message);
    }
}