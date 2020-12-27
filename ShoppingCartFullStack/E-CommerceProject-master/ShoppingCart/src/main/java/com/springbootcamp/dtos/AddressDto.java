package com.springbootcamp.dtos;

public class AddressDto extends BaseDto{

    private String city;

    private String state;

    private String country;

    private Integer zipCode;

    private String label;

    private String addressLine;

    public AddressDto() {

    }

    public AddressDto(String city, String state, String country, Integer zipCode, String label, String addressLine) {
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                ", label='" + label + '\'' +
                ", addressLine='" + addressLine + '\'' +
                '}';
    }
}
