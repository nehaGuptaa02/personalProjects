package com.springbootcamp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CATEGORY_METADATA_FIELD")
public class CategoryMetadataField extends DomainBase
{
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet;

    public CategoryMetadataField() {
    }

    public CategoryMetadataField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryMetadataFieldValues> getCategoryMetadataFieldValuesSet() {
        return categoryMetadataFieldValuesSet;
    }

    public void setCategoryMetadataFieldValuesSet(Set<CategoryMetadataFieldValues> categoryMetadataFieldValuesSet) {
        this.categoryMetadataFieldValuesSet = categoryMetadataFieldValuesSet;
    }
    public void addFieldValues(CategoryMetadataFieldValues fieldValue){
        if(fieldValue != null){
            if(categoryMetadataFieldValuesSet==null)
                categoryMetadataFieldValuesSet = new HashSet<>();

            categoryMetadataFieldValuesSet.add(fieldValue);
            fieldValue.setCategoryMetadataField(this);
        }
    }

    @Override
    public String toString() {
        return "CategoryMetadataField{" +
                "name='" + name + '\'' +
                ", categoryMetadataFieldValuesSet=" + categoryMetadataFieldValuesSet +
                '}';
    }
}
