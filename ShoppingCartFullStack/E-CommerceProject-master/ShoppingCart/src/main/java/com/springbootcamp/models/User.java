package com.springbootcamp.models;

import com.springbootcamp.security.GrantAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER")
@Transactional
/*Here UserDetails is container for core user information.
This allows non-security related user information (such as email addresses, telephone numbers etc)
to be stored in a convenient location.*/
public class User extends DomainBase implements UserDetails {
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "IS_DELETED")
    private Boolean isDeleted;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private Boolean isAccountNonLocked = true;
    @Transient
    private String confirmPassword;
    @Column(name = "FALSE_LOGIN_COUNT")
    private int falseAttemptCount;
    @Column(name = "CURRENCY_TYPE")
    private Currency currencyType;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @Transient
    List<GrantAuthorityImpl> grantAuthorities;

    //For storing images
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "FILE_PATH")
    private String filePath;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public User(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public User(String email, String password, List<GrantAuthorityImpl> grantAuthorities) {
        this.email = email;
        this.password = password;
        this.grantAuthorities = grantAuthorities;
    }


    public User(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.isDeleted = user.isDeleted();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.roles = new ArrayList<>(user.getRoles());
        List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
        this.roles.forEach(role -> grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority())));
        this.grantAuthorities = grantAuthorityList;
        this.addresses = new HashSet<Address>(user.getAddresses());
    }

    public User() {

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

    public void setPassword(String password) {
        this.password = password;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<GrantAuthorityImpl> getGrantAuthorities() {
        return grantAuthorities;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }

    public int getFalseAttemptCount() {
        return falseAttemptCount;
    }

    public void setFalseAttemptCount(int falseAttemptCount) {
        this.falseAttemptCount = falseAttemptCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", isActive=" + isActive +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", falseAttemptCount=" + falseAttemptCount +
                ", currencyType=" + currencyType +
                ", roles=" + roles +
                ", addresses=" + addresses +
                ", cart=" + cart +
                ", grantAuthorities=" + grantAuthorities +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public Currency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Currency currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(Boolean AccountNonLocked) {
        this.isAccountNonLocked = AccountNonLocked;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //To add new address in User
    public void addAddress(Address address) {
        if (address != null)
            if (addresses == null)
                addresses = new HashSet<>();
        System.out.println("Adddres added");
        address.setUser(this);
        addresses.add(address);

    }


}

