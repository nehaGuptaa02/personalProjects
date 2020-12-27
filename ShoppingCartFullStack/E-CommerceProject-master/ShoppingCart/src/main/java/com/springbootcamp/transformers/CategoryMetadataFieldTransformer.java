package com.springbootcamp.transformers;

import com.springbootcamp.dtos.CategoryMetadataFieldDto;
import com.springbootcamp.interfaces.CategoryMetadataFieldToTransformer;
import com.springbootcamp.models.CategoryMetadataField;

import java.io.IOException;

public class CategoryMetadataFieldTransformer implements CategoryMetadataFieldToTransformer<CategoryMetadataField, CategoryMetadataFieldDto>
{
    @Override
    public CategoryMetadataFieldDto toDto(CategoryMetadataField categoryMetadataField)  {
        CategoryMetadataFieldDto categoryMetadataFieldDto=new CategoryMetadataFieldDto();
        categoryMetadataFieldDto.setId(categoryMetadataField.getId());
        categoryMetadataFieldDto.setName(categoryMetadataField.getName());
        return categoryMetadataFieldDto;
    }

    @Override
    public CategoryMetadataField fromDto(CategoryMetadataFieldDto categoryMetadataFieldDto) {
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
        categoryMetadataField.setId(categoryMetadataFieldDto.getId());
        categoryMetadataField.setName(categoryMetadataFieldDto.getName());
        return categoryMetadataField;

    }
}
