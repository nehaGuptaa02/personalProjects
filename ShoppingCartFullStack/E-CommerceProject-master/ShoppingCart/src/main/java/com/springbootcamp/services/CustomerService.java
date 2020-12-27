package com.springbootcamp.services;

import com.springbootcamp.dtos.CustomerDto;
import com.springbootcamp.models.Address;
import com.springbootcamp.models.Customer;
import com.springbootcamp.models.Role;
import com.springbootcamp.models.User;
import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.events.OnRegistrationCompleteEvent;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.repos.AddressRepository;
import com.springbootcamp.repos.CustomerRepository;
import com.springbootcamp.repos.RoleRepository;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.transformers.AddressTransformer;
import com.springbootcamp.transformers.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ValidationsService validationsService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerDto registerNewCustomerAccount(CustomerDto customerDto, WebRequest webRequest) throws IOException {
        Locale locale = webRequest.getLocale();
        if (validationsService.emailExist(customerDto.getEmail())) {
            throw new ECommerceException(ErrorCode.EMAIL_ALREADY_EXITS);
        }
        if (customerDto.getEmail().isEmpty() || customerDto.getFirstName().isEmpty() || customerDto.getLastName().isEmpty() ||
                customerDto.getPassword().isEmpty() || customerDto.getConfirmPassword().isEmpty() ||
                customerDto.getEmail() == null || customerDto.getFirstName() == null || customerDto.getLastName() == null ||
                customerDto.getPassword() == null || customerDto.getConfirmPassword() == null) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }

        if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
            throw new ECommerceException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        //If the password for reset is not valid
        if (!validationsService.isPasswordValid(customerDto.getPassword())) {
            throw new ECommerceException(ErrorCode.STRONG_PASSWORD);
        }
        if (customerDto.getContact().length() != 10) {
            throw new ECommerceException((ErrorCode.INVALID_CONTACT_NUMBER));
        }
        if (!validationsService.isValidEmail(customerDto.getEmail())) {
            throw new ECommerceException(ErrorCode.INVALID_EMAIL);
        }

        Customer registerCustomer = new Customer();
        registerCustomer.setEmail(customerDto.getEmail());
        registerCustomer.setFirstName(customerDto.getFirstName());
        registerCustomer.setMiddleName(customerDto.getMiddleName());
        registerCustomer.setLastName(customerDto.getLastName());
        registerCustomer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        registerCustomer.setConfirmPassword(passwordEncoder.encode(customerDto.getConfirmPassword()));
        registerCustomer.setContact(customerDto.getContact());
        //set is enable=true
        registerCustomer.setActive(false);
        registerCustomer.setDeleted(registerCustomer.isDeleted());
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findByAuthority("ROLE_CUSTOMER"));
        registerCustomer.setRoles(roleList);
        Customer savedCustomer = customerRepository.save(registerCustomer);
        CustomerTransformer customerTransformer = new CustomerTransformer();
        customerDto = customerTransformer.toDto(savedCustomer);

        Optional<User> user = userRepository.findById(registerCustomer.getId());
        User savedUser = user.get();
        try {
            String appUrl = webRequest.getContextPath();
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, locale, savedUser));
        } catch (Exception re) {
            re.printStackTrace();
        }
        return customerDto;

    }

    //To get list of all customer
    public List<CustomerDto> getAllCustomer(Pageable pageable) {
        Iterable<Customer> customerIterable = customerRepository.findAll(pageable);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        CustomerTransformer customerTransformer = new CustomerTransformer();
        customerIterable.forEach(customer -> customerDtoList.add(customerTransformer.toDto(customer)));
        return customerDtoList;
    }

    //To view CustomerProfile
    public CustomerDto getCustomerProfile(String username) {
        Customer customer = customerRepository.findByEmail(username);
        CustomerTransformer customerTransformer = new CustomerTransformer();
        CustomerDto customerDto = customerTransformer.toDto(customer);
        return customerDto;
    }

    //To get all customersAddress List
    public List<AddressDto> getAllCustomerAddresses(String username) {
        Customer customer = customerRepository.findByEmail(username);
        Iterable<Address> addressIterable = customer.getAddresses();
        List<AddressDto> addressDtoList = new ArrayList<>();
        AddressTransformer addressTransformer = new AddressTransformer();
        addressIterable.forEach(address -> addressDtoList.add(addressTransformer.toDto(address)));
        return addressDtoList;

    }

    //To add a Customer
    public ResponseEntity addCustomerAddress(String username, AddressDto addressDto) {
        Customer customer = customerRepository.findByEmail(username);
        AddressTransformer addressTransformer = new AddressTransformer();
        Address address = addressTransformer.fromDto(addressDto);
        //If user enters a null value to any mandatory request parameters.
        if (address.getCity() == null || address.getState() == null || address.getCountry() == null ||
                address.getZipCode() == null || address.getLabel() == null || address.getAddressLine() == null ||
                address.getCity().isEmpty() || address.getState().isEmpty() || address.getCountry().isEmpty() ||
                address.getLabel().isEmpty() || address.getAddressLine().isEmpty()) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        if (addressDto.getZipCode().toString().length() != 6) {
            throw new ECommerceException(ErrorCode.INVALID_ZIPCODE);
        }
        customer.addAddress(address);
        customerRepository.save(customer);
        ResponseEntity responseEntity = new ResponseEntity("Address has been successfully added", HttpStatus.CREATED);
        return responseEntity;
    }

    //To delete a customer by its id
    public String deleteCustomerById(String username, Long id, Locale locale) {
        if (id == null) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent())
            throw new ECommerceException(ErrorCode.NO_ADDRESS_FOUND_WITH_GIVEN_ID);
        else {
            Address newAddress = address.get();
            if (!newAddress.getUser().getEmail().equals(username)) {
                throw new ECommerceException(ErrorCode.NO_USER_IS_FOUND_WITH_GIVEN_ADDRESS_ID);
            }
            addressRepository.delete(newAddress);
        }
        return messageSource.getMessage("message.deleteAddress", null, locale);
    }

    //To update a customer address by its id.
    public AddressDto updateCustomerAddress(String username, AddressDto addressDto, Long id, Locale locale) {
        if (addressDto.getCity().trim().length() == 0 && addressDto.getState().trim().length() == 0 &&
                addressDto.getCountry().trim().length() == 0 &&
                addressDto.getLabel().trim().length() == 0 && addressDto.getAddressLine().trim().length() == 0) {
            throw new ECommerceException(ErrorCode.INVALID_UPDATE);
        }
        if(id==null)
        {
            throw  new ECommerceException(ErrorCode.INVALID_ADDRESS_ID);
        }
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent())
            throw new ECommerceException(ErrorCode.NO_ADDRESS_FOUND_WITH_GIVEN_ID);
        Address savedAddress = address.get();
        User user = userRepository.findByEmail(username);
        if (!savedAddress.getUser().getEmail().equals(username))
            throw new ECommerceException(ErrorCode.NO_USER_IS_FOUND_WITH_GIVEN_ADDRESS_ID);
        //updating the address using patch
        if (addressDto.getCity() != null && addressDto.getCity().trim().length()!=0)
            savedAddress.setCity(addressDto.getCity());
        if (addressDto.getState() != null && addressDto.getState().trim().length()!=0)
            savedAddress.setState(addressDto.getState());
        if (addressDto.getCountry() != null && addressDto.getCountry().trim().length()!=0)
            savedAddress.setCountry(addressDto.getCountry());
        if (addressDto.getZipCode() != null)
            savedAddress.setZipCode(addressDto.getZipCode());
        if (addressDto.getLabel() != null && addressDto.getLabel().trim().length()!=0)
            savedAddress.setLabel(addressDto.getLabel());
        if (addressDto.getAddressLine() != null && addressDto.getAddressLine().trim().length()!=0)
            savedAddress.setAddressLine(addressDto.getAddressLine());
        addressRepository.save(savedAddress);
        AddressTransformer addressTransformer = new AddressTransformer();
        AddressDto addressDto1 = addressTransformer.toDto(savedAddress);
        return addressDto1;
    }

    //To update password of a customer
    public String updateCustomerPassword(String password, String confirmPassword, String username, Locale locale) {
        //If user enters null values
        if (password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty()) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        //If password does not match
        else if (!password.equals(confirmPassword)) {
            throw new ECommerceException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        //If the password for reset is not valid
        else if (!validationsService.isPasswordValid(password)) {
            throw new ECommerceException(ErrorCode.STRONG_PASSWORD);

        } else {
            Customer customer = customerRepository.findByEmail(username);
            customer.setPassword(passwordEncoder.encode(password));
            customerRepository.save(customer);
            emailService.sendEmail(customer.getEmail(), "Password Reset", "Your password has been successfully changed.");
            return messageSource.getMessage("message.resetPasswordConfirmation", null, locale);
        }

    }

    //Updating customer Profile
    public CustomerDto updateCustomerProfile(CustomerDto customerDto, String username, Locale locale) {
        Customer customer = customerRepository.findByEmail(username);
        if (customerDto.getEmail().trim().length() == 0 && customerDto.getFirstName().trim().length() == 0 && customerDto.getLastName().trim().length() == 0 && customerDto.getContact() == null
                || (customerDto.getEmail().isEmpty() && customerDto.getFirstName().isEmpty() && customerDto.getLastName().isEmpty() && customerDto.getContact().isEmpty())) {
            throw new ECommerceException(ErrorCode.INVALID_UPDATE);
        }
        if (customerDto.getFirstName() != null && customerDto.getFirstName().trim().length() != 0)
            customer.setFirstName(customerDto.getFirstName());
        if (customerDto.getEmail() != null && customerDto.getEmail().trim().length() != 0)
            customer.setEmail(customerDto.getEmail());
        if (customerDto.getMiddleName() != null && customerDto.getMiddleName().trim().length() != 0)
            customer.setMiddleName(customerDto.getMiddleName());
        if (customerDto.getLastName() != null && customerDto.getLastName().trim().length() != 0)
            customer.setLastName(customerDto.getLastName());
        //code to update profile picture
        if (customerDto.getContact() != null)
            customer.setContact(customerDto.getContact());
        CustomerTransformer customerTransformer = new CustomerTransformer();
        customerDto = customerTransformer.toDto(customer);
        customerRepository.save(customer);
        return customerDto;

    }
}
