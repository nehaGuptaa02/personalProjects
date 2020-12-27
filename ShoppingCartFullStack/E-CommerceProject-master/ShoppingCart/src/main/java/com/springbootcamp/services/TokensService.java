package com.springbootcamp.services;

import com.springbootcamp.models.User;
import com.springbootcamp.models.ForgotPasswordToken;
import com.springbootcamp.models.VerificationToken;
import com.springbootcamp.repos.ForgotPasswordRepository;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.repos.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokensService {
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;


    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken getVerificationTokenByUser( User user) {
        return verificationTokenRepository.findByUser(user);
    }
    public VerificationToken getVerificationTokenByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
    public void deleteVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        verificationTokenRepository.delete(verificationToken);
    }
    public void createForgotPasswordToken(String token, User user){
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken(token, user);
        forgotPasswordRepository.save(forgotPasswordToken);
    }
    public ForgotPasswordToken getForgotPasswordTokenByUser( User user) {
        return forgotPasswordRepository.findByUser(user);
    }

    public ForgotPasswordToken getForgotPasswordTokenByToken( String forgotPasswordToken) {
        return forgotPasswordRepository.findByToken(forgotPasswordToken);
    }
    public void deleteForgotPasswordToken(String token)
    {
        ForgotPasswordToken forgotPasswordToken=forgotPasswordRepository.findByToken(token);
        forgotPasswordRepository.delete(forgotPasswordToken);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }
}
