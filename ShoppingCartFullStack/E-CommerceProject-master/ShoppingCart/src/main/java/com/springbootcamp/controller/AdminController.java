package com.springbootcamp.controller;

import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.services.CustomerService;
import com.springbootcamp.services.SellerService;
import com.springbootcamp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3001")
public class AdminController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/getAllCustomer")
    public List<CustomerDto> getAllCustomer(@PageableDefault(page = 0, value = 10, sort = {"id"}, direction = Sort.Direction.ASC)
                                                    Pageable pageable) {

        return customerService.getAllCustomer(pageable);
    }

    @GetMapping("/getAllSeller")
    public List<SellerDto> getAllSeller(Pageable pageable) {
        return sellerService.getAllSeller(pageable);
    }

    @PatchMapping("/activateDeactivateCustomer/{id}")
    public @ResponseBody
    String activateCustomer(@PathVariable Long id, WebRequest webRequest, @RequestParam("choice") String choice) {
        String message = userService.activateDeactivateUserById(id, webRequest, choice);
        return message;
    }

    @PatchMapping("/activateDeactivateSeller/{id}")
    public @ResponseBody
    String deactivateSeller(@PathVariable Long id, WebRequest webRequest, @RequestParam("choice") String choice) {
        String message = userService.activateDeactivateUserById(id, webRequest, choice);
        return message;
    }
}
