package com.springbootcamp.events;

import com.springbootcamp.models.User;
import com.springbootcamp.services.EmailService;
import com.springbootcamp.services.TokensService;
import com.springbootcamp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TokensService tokensService;
    private final Logger log = LoggerFactory.getLogger(RegistrationListener.class);

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistrationEvent(event);
    }
    @Async
    private void sendMail(SimpleMailMessage email){
        javaMailSender.send(email);
    }

    public void confirmRegistrationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        log.debug(" Generated token is {}", token);
        tokensService.createVerificationToken(user, token);
        String recipientAddress = user.getEmail();
        Long id=user.getId();
        String subject = "You have been successfully registered with an id "+id+"\r\n" +"Please click on the below link to activate your account and enter the token there";
        String confirmationUrl = "http://localhost:3001" + "/activateAccount";
        String message = messageSource.getMessage("message.regSucc", null, event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl +
                "\r\n" +"Generated token:"+ token);
        javaMailSender.send(email);
    }

}
