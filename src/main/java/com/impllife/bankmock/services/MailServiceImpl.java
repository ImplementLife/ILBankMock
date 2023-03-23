package com.impllife.bankmock.services;

import com.impllife.bankmock.services.interfaces.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender emailSender;
    @Override
    public void sendMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@ilfa.dp.ua");
        message.setTo(to);
        message.setSubject("no-reply, Test mailing");
        message.setText(text + " </br>" + new SimpleDateFormat().format(new Date()));
        emailSender.send(message);
    }
}
