package com.springbootcamp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service

public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MessageSource messageSource;
    public EmailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender=javaMailSender;
    }
    private final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Async
    public void sendEmail(String toEmail,String subject,String message)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("ecommerceproject.tothenew@gmail.com");
        javaMailSender.send(simpleMailMessage);
        log.info("email has been send successfully");
    }


}
