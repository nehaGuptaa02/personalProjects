package com.springbootcamp.controller;

import com.springbootcamp.services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/forgotPassword")
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {
    @Autowired
    private ForgotPasswordService forgotPasswordService;
    @PostMapping("/")
    public String forgotPassword( WebRequest webRequest, @RequestParam("email") String email)
    {
        return forgotPasswordService.forgotPassword(webRequest,email);
    }
    @PatchMapping("/resetPassword")
    public @ResponseBody String resetPassword( WebRequest webRequest,
                                              @RequestParam("token")String token,
                                              @RequestParam ("password")String password,
                                              @RequestParam ("confirmPassword")String confirmPassword)
    {
    return forgotPasswordService.resetPassword(webRequest,token,password,confirmPassword);
    }
}
