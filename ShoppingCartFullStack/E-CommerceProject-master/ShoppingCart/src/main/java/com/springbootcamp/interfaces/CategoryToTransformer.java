package com.springbootcamp.interfaces;

import com.springbootcamp.dtos.CategoryDto;
import com.springbootcamp.models.Category;

import java.io.IOException;

public interface CategoryToTransformer<T extends Category, T1 extends CategoryDto> {

    T1 toDto(T Category) throws IOException;

    T fromDto(T1 CategoryDto);

}