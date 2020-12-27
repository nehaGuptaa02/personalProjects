package com.springbootcamp.models;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class Address extends DomainBase {

    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "ZIP_CODE")
    private Integer zipCode;
    @Column(name = "LABEL")
    private String label;
    @Column(name = "ADDRESS_LINE")
    private String addressLine;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    public Address()
    {

    }
    public Address(String city, String state, String country, Integer zipCode, String label, String addressLine) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.label = label;
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                ", label='" + label + '\'' +
                ", addressLine='" + addressLine + '\'' + '}';
    }
}
