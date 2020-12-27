package com.springbootcamp.services;

import com.springbootcamp.dtos.SellerDto;
import com.springbootcamp.enums.ErrorCode;
import com.springbootcamp.models.*;
import com.springbootcamp.repos.RoleRepository;
import com.springbootcamp.repos.SellerRepository;
import com.springbootcamp.dtos.AddressDto;
import com.springbootcamp.exceptions.ECommerceException;
import com.springbootcamp.repos.AddressRepository;
import com.springbootcamp.repos.UserRepository;
import com.springbootcamp.transformers.AddressTransformer;
import com.springbootcamp.transformers.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ValidationsService validationsService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessageSource messageSource;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //To register a new Seller
    public SellerDto registerNewSellerAccount(SellerDto sellerDto) throws IOException {
        if (validationsService.emailExist(sellerDto.getEmail())) {
            throw new ECommerceException(ErrorCode.EMAIL_ALREADY_EXITS);
        }
        if (sellerDto.getEmail().isEmpty() || sellerDto.getFirstName().isEmpty() || sellerDto.getLastName().isEmpty() ||
                sellerDto.getCompanyName().isEmpty() || sellerDto.getCompanyContact().isEmpty() || sellerDto.getGst().isEmpty() ||
                sellerDto.getPassword().isEmpty() || sellerDto.getConfirmPassword().isEmpty() ||
                sellerDto.getEmail() == null || sellerDto.getFirstName() == null || sellerDto.getLastName() == null ||
                sellerDto.getCompanyName() == null || sellerDto.getCompanyContact() == null || sellerDto.getGst() == null ||
                sellerDto.getPassword() == null || sellerDto.getConfirmPassword() == null) {
            throw new ECommerceException((ErrorCode.NULL_VALUES));
        }
//        if (validationsService.isValidEmail((sellerDto.getEmail()))) {
//            throw new ECommerceException((ErrorCode.INVALID_EMAIL));
//        }
        if (sellerDto.getCompanyContact().length() != 10) {
            throw new ECommerceException((ErrorCode.INVALID_CONTACT_NUMBER));
        }
        if (!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())) {
            throw new ECommerceException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        }
        //If the password for reset is not valid
        if (!validationsService.isPasswordValid(sellerDto.getPassword())) {
            throw new ECommerceException(ErrorCode.STRONG_PASSWORD);
        }
        if (!validationsService.isCompanyNameUnique(sellerDto.getCompanyName())) {
            throw new ECommerceException(ErrorCode.INVALID_COMPANY_NAME);
        }

        if (!validationsService.isValidGST(sellerDto.getGst())) {
            throw new ECommerceException(ErrorCode.INVALID_GST);
        }
        if (!validationsService.isGSTUnique(sellerDto.getGst())) {
            throw new ECommerceException(ErrorCode.UNIQUE_GST_NUMBER);
        }
        Seller seller = new Seller();
        seller.setEmail(sellerDto.getEmail());
        seller.setFirstName(sellerDto.getFirstName());
        seller.setMiddleName(sellerDto.getMiddleName());
        seller.setLastName(sellerDto.getLastName());
        seller.setGst(sellerDto.getGst());
        seller.setCompanyName(sellerDto.getCompanyName());
        seller.setCompanyContact(sellerDto.getCompanyContact());
        seller.setActive(true);
        seller.setDeleted(sellerDto.isDeleted());
        seller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findByAuthority("ROLE_SELLER"));
        seller.setRoles(roleList);
        Seller savedSeller = sellerRepository.save(seller);
        SellerTransformer sellerTransformer = new SellerTransformer();
        SellerDto sellerDto1 = sellerTransformer.toDto(savedSeller);
        emailService.sendEmail(seller.getEmail(), "Account creation SuccessFul", "Congratulation! Your account has been created.It's waiting for approval");
        return sellerDto1;
    }

    //To get a list of all seller
    public List<SellerDto> getAllSeller(Pageable pageable) {
        Iterable<Seller> sellerIterable = sellerRepository.findAll(pageable);
        List<SellerDto> sellerDtoList = new ArrayList<>();
        sellerIterable.forEach(seller -> sellerDtoList.add(new SellerDto(seller.getId(), seller.getFirstName(),
                seller.getMiddleName(), seller.getLastName(),
                seller.getEmail(), seller.getCompanyContact(), seller.getCompanyName(), seller.isActive())));
        return sellerDtoList;
    }

    //To view seller profile
    public SellerDto getSellerProfile(String username, Locale locale) {
        Seller seller = sellerRepository.findByEmail(username);
        SellerTransformer sellerTransformer = new SellerTransformer();
        SellerDto sellerDto = sellerTransformer.toDto(seller);
        return sellerDto;
    }

    //To update password of a seller
    public String updateSellerPassword(String password, String confirmPassword, String username, Locale locale) {
        //If user enters null values
        if (password == null || confirmPassword == null) {
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
            Seller seller = sellerRepository.findByEmail(username);
            seller.setPassword(passwordEncoder.encode(password));
            sellerRepository.save(seller);
            emailService.sendEmail(seller.getEmail(), "Password Reset", "Your password has been successfully changed.");
            return messageSource.getMessage("message.resetPasswordConfirmation", null, locale);
        }

    }

    public AddressDto updateSellerAddress(String username, AddressDto addressDto, Long id, Locale locale) {
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

    //Updating seller Profile
    public SellerDto updateSellerProfile(SellerDto sellerDto, String username, Locale locale) {
        if (sellerDto.getEmail().trim().length() == 0 && sellerDto.getFirstName().trim().length()==0 && sellerDto.getLastName().trim().length()==0
                && sellerDto.getCompanyName().trim().length()==0 && sellerDto.getCompanyContact().trim().length()==0 && sellerDto.getGst().trim().length()==0
                || (sellerDto.getEmail().isEmpty() && sellerDto.getFirstName().isEmpty() && sellerDto.getLastName().isEmpty() && sellerDto.getCompanyName().isEmpty()&& sellerDto.getGst().isEmpty())) {
            throw new ECommerceException(ErrorCode.INVALID_UPDATE);
        }
        Seller seller = sellerRepository.findByEmail(username);
        if (sellerDto.getFirstName()!= null && sellerDto.getFirstName().trim().length()!=0)
            seller.setFirstName(sellerDto.getFirstName());
        if (sellerDto.getMiddleName()!= null && sellerDto.getMiddleName().trim().length()!=0)
            seller.setMiddleName(sellerDto.getMiddleName());
        if (sellerDto.getLastName()!= null  && sellerDto.getLastName().trim().length()!=0)
            seller.setLastName(sellerDto.getLastName());
        //code to update profile picture
        if (sellerDto.getGst()!= null  && sellerDto.getGst().trim().length()!=0)
            seller.setGst(sellerDto.getGst());
        if (sellerDto.getCompanyContact()!= null)
            seller.setCompanyContact(sellerDto.getCompanyContact());
        if (sellerDto.getCompanyName() != null  && sellerDto.getCompanyName().trim().length()!=0)
            seller.setCompanyName(sellerDto.getCompanyName());
        SellerTransformer sellerTransformer = new SellerTransformer();
        sellerDto = sellerTransformer.toDto(seller);
        sellerRepository.save(seller);
        return sellerDto;

    }

    public ResponseEntity addSellerAddress(String username, AddressDto addressDto)
    {
        User user=userRepository.findByEmail(username);
        if(user==null)
        {
            throw new ECommerceException(ErrorCode.NO_USER_FOUND_WITH_GIVEN_EMAIL);
        }
        Long userId=user.getId();
        Optional<Address> sellerAddress = addressRepository.findByUserId(userId);
        if (sellerAddress.isPresent())
            throw new ECommerceException(ErrorCode.INVALID_SELLER_ADDRESS);

        Seller seller = sellerRepository.findByEmail(username);
        AddressTransformer addressTransformer = new AddressTransformer();
        Address address = addressTransformer.fromDto(addressDto);
        //If user enters a null value to any mandatory request parameters.
        if (address.getCity() == null || address.getState() == null || address.getCountry() == null ||
                address.getZipCode() == null || address.getLabel() == null || address.getAddressLine() == null||
                address.getCity().isEmpty() || address.getState().isEmpty()|| address.getCountry().isEmpty() ||
                  address.getLabel().isEmpty() || address.getAddressLine().isEmpty()
        ) {
            throw new ECommerceException(ErrorCode.NULL_VALUES);
        }
        if (addressDto.getZipCode().toString().length() != 6) {
            throw new ECommerceException(ErrorCode.INVALID_ZIPCODE);
        }
        seller.addAddress(address);
        sellerRepository.save(seller);
        ResponseEntity responseEntity = new ResponseEntity("Address has been successfully added", HttpStatus.CREATED);
        return responseEntity;
    }

    public List<AddressDto> getSellerAddress(String username) {
            Seller seller = sellerRepository.findByEmail(username);
            Iterable<Address> addressIterable = seller.getAddresses();
            List<AddressDto> addressDtoList = new ArrayList<>();
            AddressTransformer addressTransformer = new AddressTransformer();
            addressIterable.forEach(address -> addressDtoList.add(addressTransformer.toDto(address)));
            return addressDtoList;
    }

//    public SellerRegistrationDto registerNewSellerAccount(SellerDto sellerDto, AddressDto addressDto) throws IOException {
//        if (validationsService.emailExist(sellerDto.getEmail())) {
//            throw new ECommerceException(ErrorCode.EMAIL_ALREADY_EXITS);
//        }
//        Seller seller = new Seller();
//        seller.setEmail(sellerDto.getEmail());
//        seller.setFirstName(sellerDto.getFirstName());
//        seller.setMiddleName(sellerDto.getMiddleName());
//        seller.setLastName(sellerDto.getLastName());
//        seller.setGst(sellerDto.getGst());
//        seller.setCompanyName(sellerDto.getCompanyName());
//        seller.setCompanyContact(sellerDto.getCompanyContact());
//        seller.setActive(false);
//        seller.setDeleted(sellerDto.isDeleted());
//        seller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
//        List<Role> roleList = new ArrayList<>();
//        roleList.add(roleRepository.findByAuthority("ROLE_SELLER"));
//        seller.setRoles(roleList);
//        Seller savedSeller = sellerRepository.save(seller);
//        Address address=new Address();
//        address.setCity(addressDto.getCity());
//        address.setState(addressDto.getState());
//        address.setCountry(addressDto.getCountry());
//        address.setZipCode(addressDto.getZipCode());
//        address.setAddressLine(addressDto.getAddressLine());
//        SellerRegistrationDto sellerRegistrationDto = toDto(savedSeller,address);
//        emailService.sendEmail(seller.getEmail(), "Account creation SuccessFul", "Congratulation! Your account has been created.It's waiting for approval");
//        return sellerRegistrationDto;
//    }
//
//    public SellerRegistrationDto toDto(Seller seller, Address address) {
//        SellerRegistrationDto sellerRegistrationDto=new SellerRegistrationDto();
//        sellerRegistrationDto.setId(seller.getId());
//        sellerRegistrationDto.setEmail(seller.getEmail());
//        sellerRegistrationDto.setFirstName(seller.getFirstName());
//        if (seller.getMiddleName() != null)
//            sellerRegistrationDto.setMiddleName(seller.getMiddleName());
//        sellerRegistrationDto.setLastName(seller.getLastName());
//        sellerRegistrationDto.setGst(seller.getGst());
//        sellerRegistrationDto.setCompanyName(seller.getCompanyName());
//        sellerRegistrationDto.setCompanyContact(seller.getCompanyContact());
//        sellerRegistrationDto.setActive(seller.isActive());
//        sellerRegistrationDto.setDeleted(seller.isDeleted());
//        AddressDto addressDto=new AddressDto();
//        addressDto.setCity(address.getCity());
//        addressDto.setState(address.getState());
//        addressDto.setCountry(address.getCountry());
//        addressDto.setZipCode(address.getZipCode());
//        addressDto.setAddressLine(address.getAddressLine());
//        sellerRegistrationDto.setAddressDto(addressDto);
//        return sellerRegistrationDto;
//
//    }
}

