package com.springbootcamp.security;

import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
/*UserDetailsService interface is used in order to lookup the
username, password and GrantedAuthorities for any given user.*/
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encryptedPassword = passwordEncoder.encode("pass");
        System.out.println("Trying to authenticate user ::" + username);
        System.out.println("Encrypted Password ::" + encryptedPassword);
        UserDetails userDetails = userDao.loadUserByUsername(username);
        if(userDetails==null)
        {
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);
        }
        return userDetails;

    }
}




