package com.springbootcamp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category extends DomainBase {

    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Category> subcategories;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parentCategory;

    public Category()
    {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
//
    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", products=" + "" +
                ", categoryMetadataFieldValuesSet=" + categoryMetadataFieldValuesSet.size() +
                ", subcategories=" + "" +
                ", parentCategory=" + "" +
                '}';
    }

    public void addSubCategory(Category category) {
        if (category != null)
            if (subcategories == null)
                subcategories = new HashSet<>();
        category.setParentCategory(this);
        subcategories.add(category);
    }

    public void addProduct(Product product) {
        if (product != null)
            if (products == null)
                products = new HashSet<>();
        product.setCategory(this);
        products.add(product);

    }


    public void addFieldValues(CategoryMetadataFieldValues fieldValue){
        if(fieldValue != null){
            if(categoryMetadataFieldValuesSet==null)
                categoryMetadataFieldValuesSet = new HashSet<>();

            categoryMetadataFieldValuesSet.add(fieldValue);
            fieldValue.setCategory(this);
        }
    }


}
