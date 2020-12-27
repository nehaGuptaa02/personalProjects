package com.springbootcamp.models;

import com.springbootcamp.security.GrantAuthorityImpl;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "CUSTOMER")
public class Customer extends User {
    @Column(name = "CONTACT")
    private String contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;

    public Customer() {

    }

    public Customer(String email, String password, List<GrantAuthorityImpl> grantAuthorities, String contact, Set<Order> orders, Set<ProductReview> productReviews) {
        super(email, password, grantAuthorities);
        this.contact = contact;
        this.orders = orders;
        this.productReviews = productReviews;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

}
