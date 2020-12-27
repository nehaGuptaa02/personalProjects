package com.springbootcamp.dtos;

import java.util.Set;

public class CategoryAdminResponseDto {

    CategoryDto categoryDto;
    Set<CategoryDto> subCategories;
    Set<CategoryMetadataFieldDto> categoryMetadataFieldDtoSet;

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public Set<CategoryDto> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<CategoryDto> subCategories) {
        this.subCategories = subCategories;
    }

    public Set<CategoryMetadataFieldDto> getCategoryMetadataFieldDtoSet() {
        return categoryMetadataFieldDtoSet;
    }

    public void setCategoryMetadataFieldDtoSet(Set<CategoryMetadataFieldDto> categoryMetadataFieldDtoSet) {
        this.categoryMetadataFieldDtoSet = categoryMetadataFieldDtoSet;
    }
}
