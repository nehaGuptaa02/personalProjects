package com.springbootcamp.dtos;

import com.springbootcamp.validators.ValidCompanyName;
import com.springbootcamp.validators.ValidGST;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SellerDto extends UserDto {


    private String gst;


    private String companyContact;


    private String companyName;


    public SellerDto() {

    }

    public SellerDto(Long id,  String firstName,
                     String middleName,
                      String lastName,
                      String email,
                      String companyName, String companyContact,
                     Boolean active) {
        this.setId(id);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
        this.setCompanyName(companyName);
        this.setCompanyContact(companyContact);
        this.setActive(active);
    }

    public SellerDto( String email, String firstName,
                     String middleName,  String lastName,
                      String gst, String companyContact,
                      String companyName) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
        this.gst = gst;
        this.companyContact = companyContact;
        this.companyName = companyName;
    }


    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "SellerDto{" +
                "gst='" + gst + '\'' +
                ", companyContact='" + companyContact + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}

