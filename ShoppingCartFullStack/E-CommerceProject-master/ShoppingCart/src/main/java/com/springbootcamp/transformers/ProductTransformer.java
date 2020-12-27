package com.springbootcamp.transformers;

import com.springbootcamp.dtos.ProductDto;
import com.springbootcamp.interfaces.ProductToTransformer;
import com.springbootcamp.models.Product;

public class ProductTransformer implements ProductToTransformer<Product, ProductDto>
{
    @Override
    public ProductDto toDto(Product product)  {
        ProductDto productDto=new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setDescription(product.getDescription());
        productDto.setActive(product.isActive());
        productDto.setCancellable(product.isCancellable());
        productDto.setReturnable(product.isReturnable());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setDeleted(product.isDeleted());
        return productDto;
    }

    @Override
    public Product fromDto(ProductDto productDto) {
        Product product=new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setActive(false);
        product.setCancellable(false);
        product.setReturnable(false);
        product.setDeleted(false);
        return product;

    }
}
