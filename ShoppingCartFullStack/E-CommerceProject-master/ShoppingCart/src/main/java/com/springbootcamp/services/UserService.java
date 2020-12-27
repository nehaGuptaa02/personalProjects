package com.springbootcamp.services;

import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.User;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.repos.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageSource messageSource;

    public String activateDeactivateUserById(Long id, WebRequest webRequest, String choice) {
        if(id==null||choice==null||choice.isEmpty())
        {
            throw new ECommerceException((ErrorCode.NULL_VALUES));
        }
        Optional<User> user = userRepository.findById(id);
        String message = "";
        if (!user.isPresent())
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_ID);
        else {
            if (choice.equalsIgnoreCase("activate")) {
                User savedUser = user.get();
                if (savedUser.isActive() == true)
                    throw new ECommerceException(ErrorCode.USER_IS_ALREADY_ACTIVE);
                else {
                    savedUser.setActive(true);
                    userRepository.save(savedUser);
                    Locale locale = webRequest.getLocale();
                    String subject = "Account Activation Sucess";
                    String msg = messageSource.getMessage("message.activationSuccessful", null, locale);
                    emailService.sendEmail(savedUser.getEmail(), subject, msg);
                    message = "user is activated";
                }
            }
            if (choice.equalsIgnoreCase("deactivate")) {
                User savedUser = user.get();
                if (savedUser.isActive() == false)
                    throw new ECommerceException(ErrorCode.USER_IS_NOT_ACTIVE);
                else {
                    savedUser.setActive(false);
                    userRepository.save(savedUser);
                    Locale locale = webRequest.getLocale();
                    String subject = "Account Deactivation";
                    String msg = messageSource.getMessage("message.deactivation", null, locale);
                    emailService.sendEmail(savedUser.getEmail(), subject, msg);
                    message = "user has been de-activated";
                }
            }
        }
        return message;
    }
}

