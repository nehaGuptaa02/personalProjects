package com.springbootcamp.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerDto extends UserDto {
    private String contact;
    private String filePath;
    public CustomerDto() {

    }

    public CustomerDto( String email, String firstName, String middleName,
                        String lastName,String contact,String password,
                       String confirmPassword) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
        this.contact = contact;
    }

    public CustomerDto(Long id,  String firstName,String middleName,
                        String lastName,  String email,Boolean active) {
        this.setId(id);
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
        this.setActive(active);

    }

    public String getContact() {
        return contact;
    }

    public void setContact(@NotEmpty String contact) {
        this.contact = contact;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "contact='" + contact + '\'' +
                '}';
    }
}
