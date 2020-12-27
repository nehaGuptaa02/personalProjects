package com.springbootcamp.models;

import javax.persistence.*;

@Entity
@Table(name = "CART")
public class Cart extends DomainBase {

    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "IS_WISHLIST_ITEM")
    private Boolean isWishlistItem;

    @OneToOne
    @JoinColumn(name = "PRODUCT_VARIATION_ID")
    private ProductVariation productVariation;

    @OneToOne
    @JoinColumn(name = "CUSTOMER_USER_ID")
    private User user;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isWishlistItem() {
        return isWishlistItem;
    }

    public void setWishlistItem(Boolean wishlistItem) {
        isWishlistItem = wishlistItem;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
