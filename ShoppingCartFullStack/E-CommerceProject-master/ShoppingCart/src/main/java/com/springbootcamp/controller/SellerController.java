package com.springbootcamp.controller;

import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.repos.SellerRepository;
import com.springbootcamp.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SellerService sellerService;

    @GetMapping("/home")
    public String sellerHome() {
        return "Seller Home";
    }

    //View an image and
    @GetMapping("/profile")
    public SellerDto getSellerProfile(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        String username = principal.getName();
        Locale locale = httpServletRequest.getLocale();
        return sellerService.getSellerProfile(username,locale);
    }
    @PatchMapping("/updateProfile")
    public @ResponseBody
    SellerDto updateSellerProfile(@RequestBody SellerDto sellerDto, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return sellerService.updateSellerProfile(sellerDto,username,locale);
    }
    @PatchMapping("/updatePassword")
    public @ResponseBody
    String updateSellerPassword(@RequestParam("password") String password,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return sellerService.updateSellerPassword(password,confirmPassword,username,locale);
    }
    @PatchMapping("/updateAddress/{id}")
    public@ResponseBody
    AddressDto updateSellerAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable Long id, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return sellerService.updateSellerAddress(username,addressDto,id,locale);

    }
    @PostMapping("/addAddress")
    public ResponseEntity addSellerAddress(@Valid @RequestBody AddressDto addressDto, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return sellerService.addSellerAddress(username,addressDto);
    }
    @GetMapping("/address")
    public List<AddressDto> getSellerAddress(HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return sellerService.getSellerAddress(username);
    }
}
