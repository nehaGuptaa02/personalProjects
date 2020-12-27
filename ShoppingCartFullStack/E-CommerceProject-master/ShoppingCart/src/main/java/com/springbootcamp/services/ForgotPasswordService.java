package com.springbootcamp.services;

import com.springbootcamp.models.User;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.ForgotPasswordToken;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.repos.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Service
public class ForgotPasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private TokensService tokensService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ValidationsService validationsService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String forgotPassword(WebRequest webRequest, String email) {
        Locale locale = webRequest.getLocale();
        User user = userRepository.findByEmail(email);
        if (email == null || email.isEmpty()) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        if (user == null)
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);
        if (user.isActive().equals(false))
            throw new ECommerceException(ErrorCode.USER_IS_NOT_ACTIVE);

        // create and save the token
        String token = UUID.randomUUID().toString();
        tokensService.createForgotPasswordToken(token, user);
        // prepare the email contents
        String recipientAddress = user.getEmail();
        String subject = "Forgot Password Please enter the generated token on the following link";
        String confirmationUrl = "http://localhost:3001" + "/forgotPasswordReset";
        String emailMessage = confirmationUrl+"\r\n"+"Generated token: " + token + "\r\n" + "please click on this link to reset your password";
        emailService.sendEmail(recipientAddress, subject, emailMessage);
//        System.out.println(confirmationUrl + token);
//        System.out.println(token);

        return messageSource.getMessage("message.MailSucess", null, locale);
    }

    public String resetPassword(WebRequest webRequest, String token, String password, String confirmPassword) {
        if (token == null || password == null || confirmPassword == null ||
                token.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        Locale locale = webRequest.getLocale();
        ForgotPasswordToken forgotPasswordToken = tokensService.getForgotPasswordTokenByToken(token);
        Calendar calendar = Calendar.getInstance();
        //If the token does not exist in database
        if (forgotPasswordToken == null) {
            throw new ECommerceException(ErrorCode.TOKEN_NOT_FOUND);
        }
        //If password does not match
         if (password.equals(confirmPassword) == false) {
            throw new ECommerceException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        //If the password for reset is not valid
        if (validationsService.isPasswordValid(password) == false) {
            throw new ECommerceException(ErrorCode.STRONG_PASSWORD);

        }
        //If the token is expired
         if (forgotPasswordToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            tokensService.deleteVerificationToken(forgotPasswordToken.getToken());
             throw new ECommerceException(ErrorCode.EXPIRED_ACTIVATION_TOKEN);
        }
            User user = forgotPasswordToken.getUser();
            user.setPassword(passwordEncoder.encode(password));
            tokensService.saveUser(user);
            tokensService.deleteForgotPasswordToken(token);
            emailService.sendEmail(user.getEmail(), "Password reset Confirmation", "Your password has been changed successfully");
            return messageSource.getMessage("message.resetPasswordConfirmation", null, locale);

    }
}
