package com.springbootcamp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "SELLER")
public class Seller extends User {
    @Column(name = "GST")
    private String gst;
    @Column(name = "COMPANY_CONTACT")
    private String companyContact;
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products;


//    @Column(unique = true)/
//    private String companyNameInToLoweCase;
////    @PrePersist@PreUpdate private prepare()
////    {
////        this.companyNameInToLoweCase=companyName==null?null:companyName.toLowerCase();
////    }


    public Seller() {
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    public void addProduct(Product product)
    {
        if (product != null)
            if ( products== null)
        products = new HashSet<>();
        System.out.println("Adddres added");
        product.setSeller(this);
        products.add(product);
    }
}
