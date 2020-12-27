package com.springbootcamp.models;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY_METADATA_FIELD_VALUES")
public class CategoryMetadataFieldValues {

    @EmbeddedId
    private CategoryMetadataFieldValueCompositeKey id = new CategoryMetadataFieldValueCompositeKey();
    @Column(name = "CATEGORY_VALUES")
    private String categoryValues;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId("categoryId")//This is used for mapping embeddedId
    private Category category;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @MapsId("categoryMetadataFieldId")

    private CategoryMetadataField categoryMetadataField;

    private String value;
    public CategoryMetadataFieldValues() {
    }

    public CategoryMetadataFieldValues(String value) {
        this.value = value;
    }


    public CategoryMetadataFieldValueCompositeKey getId() {
        return id;
    }

    public void setId(CategoryMetadataFieldValueCompositeKey id) {
        this.id = id;
    }

    public String getCategoryValues() {
        return categoryValues;
    }

    public void setCategoryValues(String categoryValues) {
        this.categoryValues = categoryValues;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategoryMetadataField getCategoryMetadataField() {
        return categoryMetadataField;
    }

    public void setCategoryMetadataField(CategoryMetadataField categoryMetadataField) {
        this.categoryMetadataField = categoryMetadataField;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
