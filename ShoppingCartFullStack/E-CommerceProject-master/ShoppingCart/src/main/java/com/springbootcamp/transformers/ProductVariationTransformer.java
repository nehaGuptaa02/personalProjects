package com.springbootcamp.transformers;

import com.springbootcamp.interfaces.ProductVariationToTransformer;
import com.springbootcamp.models.ProductVariation;
import com.springbootcamp.dtos.ProductVariationDto;

public class ProductVariationTransformer implements ProductVariationToTransformer<ProductVariation, ProductVariationDto> {
    @Override
    public ProductVariationDto toDto(ProductVariation productVariation)
    {
     ProductVariationDto productVariationDto=new ProductVariationDto();
        productVariationDto.setId(productVariation.getId());
        productVariationDto.setQuantityAvailable(productVariation.getQuantityAvailable());
        productVariationDto.setPrimaryImageName(productVariation.getPrimaryImageName());
        productVariationDto.setPrice(productVariation.getPrice());
        productVariationDto.setMetadata(productVariation.getMetadata());
        productVariationDto.setActive(true);
        productVariationDto.setProductId(productVariation.getProduct().getId());
        return productVariationDto;
    }

    @Override
    public ProductVariation fromDto(ProductVariationDto productVariationDto) {
        ProductVariation productVariation=new ProductVariation();
        productVariation.setId(productVariationDto.getId());
        productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable());
        productVariation.setPrimaryImageName(productVariationDto.getPrimaryImageName());
        productVariation.setPrice(productVariationDto.getPrice());
        productVariation.setMetadata(productVariationDto.getMetadata());
        productVariation.setActive(true);
        return productVariation;
    }
}
