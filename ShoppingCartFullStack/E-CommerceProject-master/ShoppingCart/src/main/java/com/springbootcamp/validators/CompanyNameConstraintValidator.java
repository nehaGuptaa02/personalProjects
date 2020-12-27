package com.springbootcamp.validators;

import com.springbootcamp.services.SellerService;
import com.springbootcamp.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CompanyNameConstraintValidator implements ConstraintValidator<ValidCompanyName,String> {

    @Autowired
     private SellerService sellerService;
    @Autowired
    private ValidationsService validationsService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && validationsService.isCompanyNameUnique(value);
    }
}
