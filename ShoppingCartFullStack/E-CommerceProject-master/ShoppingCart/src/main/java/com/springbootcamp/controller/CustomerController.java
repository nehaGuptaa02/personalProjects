package com.springbootcamp.controller;

import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/home")
    public String adminHome() {
        return "Customer home";
    }
    @GetMapping("/profile")
    public CustomerDto getCustomerProfile(HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();//To get the details of currently logged in user.
        String username=principal.getName();//To get the username
        return customerService.getCustomerProfile(username);
    }
    @GetMapping("/addresses")
    public List<AddressDto> getAllCustomerAddresses(HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return customerService.getAllCustomerAddresses(username);
    }
    @PatchMapping("/updatePassword")
    public @ResponseBody String updateCustomerPassword(@RequestParam("password") String password,
                                         @RequestParam("confirmPassword") String confirmPassword,
                                         HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return customerService.updateCustomerPassword(password,confirmPassword,username,locale);
    }
    @PatchMapping("/updateProfile")
    public @ResponseBody CustomerDto updateCustomerProfile(@RequestBody CustomerDto customerDto,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return customerService.updateCustomerProfile(customerDto,username,locale);
    }
    @PostMapping("/addAddress")
    public ResponseEntity addCustomerAddress(@Valid @RequestBody AddressDto addressDto, HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        return customerService.addCustomerAddress(username,addressDto);
    }
    @DeleteMapping("/deleteAddress/{id}")
    public String deleteAddressById(@PathVariable Long id,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return customerService.deleteCustomerById(username,id,locale);
    }
    @PatchMapping("/updateAddress/{id}")
    public@ResponseBody AddressDto updateCustomerAddress(@Valid @RequestBody AddressDto addressDto,@PathVariable Long id,HttpServletRequest httpServletRequest)
    {
        Principal principal=httpServletRequest.getUserPrincipal();
        String username=principal.getName();
        Locale locale=httpServletRequest.getLocale();
        return customerService.updateCustomerAddress(username,addressDto,id,locale);

    }
}
