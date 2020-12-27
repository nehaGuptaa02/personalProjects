package com.springbootcamp.services;

import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.models.Customer;
import com.springbootcamp.models.Seller;
import com.springbootcamp.models.User;
import com.springbootcamp.repos.CustomerRepository;
import com.springbootcamp.repos.SellerRepository;
import com.springbootcamp.repos.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginLogoutService {
    @Autowired
    private DefaultTokenServices defaultTokenServices;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ValidationsService validationsService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private TokensService tokensService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Logger log = LoggerFactory.getLogger(LoginLogoutService.class);

    public Map login(WebRequest webRequest, String email, String password, String clientId, String clientSecret) throws Exception {

        Locale locale = webRequest.getLocale();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        User user = (User) authentication.getPrincipal();
        if (!user.isActive()) {
            throw new ECommerceException(ErrorCode.USER_IS_NOT_ACTIVE);
        }
        if (!user.isAccountNonLocked()) {
            String message = messageSource.getMessage("message.userAccountLocked", null, locale);
            String subject = "Account Locked";
            emailService.sendEmail(user.getEmail(), subject, message);
            throw new ECommerceException(ErrorCode.ACCOUNT_LOCKED);
        }
        if (password.length() == 0 || clientId.length() == 0 || clientSecret.length() == 0 || email.length() == 0) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {

            lockUser(password, email);
            throw new ECommerceException(ErrorCode.INVALID_PASSWORD);

        }
        return createToken(clientId, clientSecret, email, password);

    }

    public Map createToken(String clientId, String clientSecret, String email, String password) {
        User savedUser = userRepository.findByEmail(email);
        savedUser.setFalseAttemptCount(0);
        userRepository.save(savedUser);

        RestTemplate restTemplate = new RestTemplate();

        // According OAuth documentation we need to send the client id and secret key in the header for authentication
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String access_token_url = "http://localhost:8080/oauth/token";
        access_token_url += "?grant_type=password&client_id=" + clientId + "&client_secret=" + clientSecret + "&password=" + password + "&username=" + email;

        ResponseEntity<Map> response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, Map.class);

        System.out.println("Access Token Response ---------------------" + response.getBody());
        return response.getBody();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void lockUser(String password, String email) {
        User savedUser = userRepository.findByEmail(email);
        int temp = savedUser.getFalseAttemptCount();
        savedUser.setFalseAttemptCount(++temp);
        log.debug("temp {}", temp);
        if (temp == 3) {
            savedUser.setAccountNonLocked(false);
           savedUser.setFalseAttemptCount(0);

        }
        userRepository.save(savedUser);
        log.debug("temp {}", temp);
        log.debug("invalid Password, {},{}", email, password);
    }

    public String unlockAccount(String username,Locale locale)
    {
        String token = UUID.randomUUID().toString();
        emailService.sendEmail(username,"Account Unlock email",token);
        return messageSource.getMessage("message.accountUnlockEmail",null,locale);
    }

    public String typeOfUser(String username, Locale locale)
    {
        String type="";
        int flag=0;
        Customer customer=customerRepository.findByEmail(username);
        Seller seller=sellerRepository.findByEmail(username);
        if(username.equalsIgnoreCase("neha.guptajune02@gmail.com"))
        {
            type="admin";
            flag=1;
        }
        if(customer==null && seller==null && flag==0 )
        {
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);
        }

        if(customer==null&& seller!=null)
        {
            type="seller";
        }
        if(customer!=null&& seller==null) {
            type = "customer";
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+type);
        return type;

    }

    public String firstNameOfUser(String username, Locale locale)
    {
        User user=userRepository.findByEmail(username);
        String firstName=user.getFirstName();
        return  firstName;
    }
}
