package com.springbootcamp.models;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "VERIFICATION_TOKEN")
public class VerificationToken extends DomainBase {
    private static final int EXPIRATION = 60 * 24;
    @Column(name = "TOKEN")
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToOne(targetEntity =Role.class,fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(/*nullable = false,*/name = "role_id")
    private Role role;

    @Column(name="EXPIRY_DATE")
    private Date expiryDate;
    public VerificationToken()
    {

    }

    public VerificationToken(String token, User user, Role role, Date expiryDate) {
        this.token = token;
        this.user = user;
        this.role = role;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public VerificationToken(User user, String token) {
        this.token=token;
        this.user=user;
        this.expiryDate=calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", role=" + role +
                ", expiryDate=" + expiryDate +
                '}';
    }



}