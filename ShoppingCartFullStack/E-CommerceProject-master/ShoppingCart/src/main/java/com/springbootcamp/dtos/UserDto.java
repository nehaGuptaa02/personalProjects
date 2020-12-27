package com.springbootcamp.dtos;

import com.springbootcamp.validators.PasswordMatches;
import com.springbootcamp.validators.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@PasswordMatches
public class UserDto extends BaseDto {

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotEmpty
    @NotNull
    private String firstName;
    private String middleName;

    @NotEmpty
    @NotNull
    private String lastName;


    @ValidPassword
    private String password;
    @ValidPassword
    private String confirmPassword;

    private Boolean isDeleted;

    private Boolean isActive;

    private Set<AddressDto> addressDtoSet;
    private String filePath;

    public UserDto() {

    }

    public UserDto(@NotNull@NotEmpty String email,
                   @NotNull@NotEmpty String firstName,
                   String middleName,
                   @NotNull@NotEmpty String lastName,
                   @NotNull@NotEmpty String password,
                   @NotNull@NotEmpty String confirmPassword) {
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<AddressDto> getAddressDtoSet() {
        return addressDtoSet;
    }

    public void setAddressDtoSet(Set<AddressDto> addressDtoSet) {
        this.addressDtoSet = addressDtoSet;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                '}';
    }
}
