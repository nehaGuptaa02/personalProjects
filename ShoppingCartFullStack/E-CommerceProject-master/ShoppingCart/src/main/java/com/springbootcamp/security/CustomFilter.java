package com.springbootcamp.security;

import com.springbootcamp.models.User;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomFilter extends DaoAuthenticationProvider {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken
            authentication) throws AuthenticationException {

//        if (authentication.getCredentials() == null){
//            throw  new BadCredentialsException("User Credentials are wrong");
//        }
//        String currentPassword = authentication.getCredentials().toString();
//        if (!passwordEncoder.matches(currentPassword,userDetails.getPassword())){
////            User user =(User)userDetails;
//            User user=userRepository.findByEmail(userDetails.getUsername());
//            Integer temp = user.getFalseAttemptCount();
//            user.setFalseAttemptCount(++temp);
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+temp);
//            if (temp == 3){
//                user.setAccountNonLocked(false);
//                emailService.sendEmail(userDetails.getUsername(),"Account Locked","Your account has been locked for successive login failures");
//            }
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+temp);
//            userRepository.save(user);
//            throw new BadCredentialsException("User Credentials are wrong");
//        }
    }

    @Override
        protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
//        User user1 = userRepository.findByEmail(user.getUsername());
//        user1.setFalseAttemptCount(0);
//        userRepository.save(user1);
        return super.createSuccessAuthentication(principal,authentication,user);
    }
}
