package com.springbootcamp.controller;

import com.springbootcamp.services.LoginLogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginLogOutController
{
    @Autowired
    private LoginLogoutService loginLogoutService;
    @Autowired
    private TokenStore tokenStore;
    @PostMapping("/login")
    public Map login(WebRequest webRequest,
                             @RequestParam("username") String email,
                             @RequestParam("password")String password,
                             @RequestParam("client_id")String clientId,
                             @RequestParam("client_secret")String clientSecret) throws Exception {
     return loginLogoutService.login(webRequest,email,password,clientId,clientSecret);
    }


    @GetMapping("/logoutUser")
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Principal principal =request.getUserPrincipal();
        String username=principal.getName();
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        String message=username+" have logged out successfully";
        return  message;
    }
    @PostMapping("/unlockAccount")
    public String unlockAccount(HttpServletRequest httpServletRequest)
    {
        Principal principal =httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return loginLogoutService.unlockAccount(username,locale);
    }
    @GetMapping("/typeOfUser")
    public String typeOfUser(HttpServletRequest httpServletRequest)
    {
        Principal principal =httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return loginLogoutService.typeOfUser(username,locale);
    }
    @GetMapping("/firstName")
    public String firstNameOfUser(HttpServletRequest httpServletRequest)
    {
        Principal principal =httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return loginLogoutService.firstNameOfUser(username,locale);
    }
}
