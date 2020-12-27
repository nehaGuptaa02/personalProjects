package com.springbootcamp.interfaces;

import com.springbootcamp.models.ProductVariation;
import com.springbootcamp.dtos.ProductVariationDto;

import java.io.IOException;

public interface ProductVariationToTransformer<T extends ProductVariation,T1 extends ProductVariationDto> {


    T1 toDto(T ProductVariation) throws IOException;

    T fromDto(T1 ProductVariationDto);

}