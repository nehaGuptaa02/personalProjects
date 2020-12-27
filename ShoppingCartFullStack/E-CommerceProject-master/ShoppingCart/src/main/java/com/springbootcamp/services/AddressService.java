package com.springbootcamp.services;

import com.springbootcamp.models.Address;
import com.springbootcamp.models.User;
import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.repos.AddressRepository;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.transformers.AddressTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    public ResponseEntity addUserAddress( AddressDto addressDto,Long id) {
        Optional<User> user = userRepository.findById(id);
        User savedUser=user.get();
        AddressTransformer addressTransformer = new AddressTransformer();
        Address address = addressTransformer.fromDto(addressDto);
        //If user enters a null value to any mandatory request parameters.
        if (address.getCity() == null || address.getState() == null || address.getCountry() == null ||
                address.getZipCode() == null || address.getLabel() == null || address.getAddressLine() == null) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        savedUser.addAddress(address);
        userRepository.save(savedUser);
        ResponseEntity responseEntity = new ResponseEntity("Address has been successfully added", HttpStatus.CREATED);
        return responseEntity;
    }
}
