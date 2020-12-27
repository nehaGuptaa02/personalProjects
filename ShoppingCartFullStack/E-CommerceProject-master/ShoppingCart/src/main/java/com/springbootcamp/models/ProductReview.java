package com.springbootcamp.models;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_REVIEW")
public class ProductReview extends DomainBase {

    @Column(name = " REVIEW")
    private String review;
    @Column(name = "RATING")
    private Integer rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_USER_ID")
    private Customer customer;


}
