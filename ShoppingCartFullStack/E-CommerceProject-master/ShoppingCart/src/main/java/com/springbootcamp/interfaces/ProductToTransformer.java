package com.springbootcamp.interfaces;

import com.springbootcamp.models.Product;
import com.springbootcamp.dtos.ProductDto;

import java.io.IOException;

public interface ProductToTransformer<T extends Product,T1 extends ProductDto> {


    T1 toDto(T Product) throws IOException;

    T fromDto(T1 ProductDto);

}