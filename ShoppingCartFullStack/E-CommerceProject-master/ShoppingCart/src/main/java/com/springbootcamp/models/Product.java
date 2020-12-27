package com.springbootcamp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
public class Product extends DomainBase {

    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "IS_CANCELLABLE")
    private Boolean isCancellable;
    @Column(name = "IS_RETURNABLE")
    private Boolean isReturnable;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "SELLER_USER_ID")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductVariation> productVariations;

    public Product()
    {

    }

    public Product(String name, String description, String brand, Boolean isCancellable, Boolean isReturnable, Boolean isActive, Boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.isCancellable = isCancellable;
        this.isReturnable = isReturnable;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", isActive=" + isActive +
                ", seller=" + seller +
                ", category=" + "" +
                ", productReviews=" + productReviews.size() +
                ", productVariations=" + productVariations.size() +
                '}';
    }

    public void addVariation(ProductVariation productVariation)
    {
        if(productVariation!=null)
            if(productVariations==null)
                productVariations=new HashSet<>();
            productVariations.add(productVariation);
            productVariation.setProduct(this);

    }
}
