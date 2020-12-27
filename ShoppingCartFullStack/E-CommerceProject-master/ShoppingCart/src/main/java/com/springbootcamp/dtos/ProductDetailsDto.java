package com.springbootcamp.dtos;

import com.springbootcamp.models.Product;

public class ProductDetailsDto
{
    private ProductDto productDto;
    private ProductVariationDto productVariationDto;

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public ProductVariationDto getProductVariationDto() {
        return productVariationDto;
    }

    public void setProductVariationDto(ProductVariationDto productVariationDto) {
        this.productVariationDto = productVariationDto;
    }

    @Override
    public String toString() {
        return "ProductDetailsDto{" +
                "productDto=" + productDto +
                ", productVariationDto=" + productVariationDto +
                '}';
    }
}
