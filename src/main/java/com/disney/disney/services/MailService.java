package com.disney.disney.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    
    private JavaMailSender mailSender;

    @Async
    public void send(String text, String title, String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setFrom("disney@mail.com");
        message.setSubject(title);
        message.setText(text);
        mailSender.send(message);
    }
}
