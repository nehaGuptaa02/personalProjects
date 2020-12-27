package com.springbootcamp.dtos;



import java.util.List;

public class MetadataFieldValueInsertDto {

    private Long categoryId;
    private List<CategoryMetadataFieldDto> fieldValues;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<CategoryMetadataFieldDto> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<CategoryMetadataFieldDto> fieldValues) {
        this.fieldValues = fieldValues;
    }

    @Override
    public String toString() {
        return "MetadataFieldValueInsertDto{" +
                "categoryId=" + categoryId +
                ", fieldValues=" + fieldValues +
                '}';
    }
}
