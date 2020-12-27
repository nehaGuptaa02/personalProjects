package com.springbootcamp.controller;


import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.repos.CustomerRepository;
import com.springbootcamp.services.ActivationLinkService;
import com.springbootcamp.services.AddressService;
import com.springbootcamp.services.CustomerService;
import com.springbootcamp.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/registration")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    SellerService sellerService;
    @Autowired
    private ActivationLinkService activationLinkService;
    @Autowired
    private AddressService addressService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @PostMapping("/customer")
    public CustomerDto registerCustomer(@RequestBody CustomerDto customerDto, WebRequest webRequest) throws IOException {
        customerDto = customerService.registerNewCustomerAccount(customerDto,webRequest);
        return customerDto;

    }
    @PostMapping("/seller")
    public SellerDto registerSeller( @RequestBody SellerDto sellerDto, WebRequest webRequest)throws IOException
    {
        return sellerService.registerNewSellerAccount(sellerDto);
    }

    @PutMapping("/activationLink")
    public String confirmActivation(WebRequest webRequest, @RequestParam("token") String token) {
        return activationLinkService.confirmActivation(webRequest, token);
    }
    @PostMapping("/resendActivationLink")
    public String resendActivationLink(WebRequest webRequest, @RequestParam("email") String email)
    {
//        Principal principal = httpServletRequest.getUserPrincipal();
//        String username = principal.getName();
        return activationLinkService.resendActivationLink(webRequest,email);
    }
    @PostMapping("addAddress/{id}")
    public ResponseEntity addUserAddress(@Valid @RequestBody AddressDto addressDto,@PathVariable Long id)
    {
        return addressService.addUserAddress(addressDto,id);
    }


}
