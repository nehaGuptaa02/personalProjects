package com.springbootcamp.services;

import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.events.OnRegistrationCompleteEvent;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.User;
import com.springbootcamp.models.VerificationToken;
import com.springbootcamp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;

@Service
public class ActivationLinkService {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private TokensService tokensService;
    @Autowired
    private EmailService emailService;

    public String confirmActivation(WebRequest webRequest, String token) {
        Locale locale = webRequest.getLocale();
        //If the token does not exist in database
        VerificationToken verificationToken = tokensService.getVerificationTokenByToken(token);
        if(verificationToken==null)
        {
            throw new ECommerceException(ErrorCode.TOKEN_NOT_FOUND);
        }
        User user = verificationToken.getUser();
        if(user.isActive().equals(true))
        {
            throw new ECommerceException(ErrorCode.USER_IS_ALREADY_ACTIVE);
        }
        if (verificationToken == null) {
            throw new ECommerceException(ErrorCode.INVALID_ACTIVATION_TOKEN);

        }
        //If the token is expired then generating a new token and returning to user

        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            try {
                String appUrl = webRequest.getContextPath();
                tokensService.deleteVerificationToken(token);
                applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, locale, user));
            } catch (Exception re) {
                re.printStackTrace();
            }
            throw new ECommerceException(ErrorCode.EXPIRED_ACTIVATION_TOKEN);
        }
        //If a valid token is entered
        user.setActive(true);
        tokensService.saveUser(user);
        tokensService.deleteVerificationToken(token);
        emailService.sendEmail(user.getEmail(), "Account Activation Success", "Your account has been sucessfully activated");
        return messageSource.getMessage("message.confirmActivation", null, locale);


    }

    public String resendActivationLink(WebRequest webRequest, String email) {
//        if(!username.equalsIgnoreCase(email))
//        {
//            throw new ECommerceException(ErrorCode.REGISTERED_EMAIL);
//
//        }
        if(email==null| email.isEmpty()|| email.trim().length()==0)
        {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        User user = userRepository.findByEmail(email);
        Locale locale = webRequest.getLocale();

        if (user == null)
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);
        if (user.isActive() == true)
            throw new ECommerceException(ErrorCode.USER_IS_ALREADY_ACTIVE);
        VerificationToken verificationToken = tokensService.getVerificationTokenByUser(user);
        tokensService.deleteVerificationToken(verificationToken.getToken());
        try {
            String appUrl = webRequest.getContextPath();
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, locale, user));
        } catch (Exception re) {
            re.printStackTrace();
        }
        return messageSource.getMessage("message.reactivationLink", null, locale);
    }
}

