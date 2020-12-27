package com.springbootcamp.transformers;

import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.interfaces.CategoryToTransformer;
import com.springbootcamp.models.Category;

public class CategoryTransformer implements CategoryToTransformer<Category, CategoryDto>
{
    @Override
    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;

    }

    @Override
    public Category fromDto(CategoryDto categoryDto) {
        Category category=new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
