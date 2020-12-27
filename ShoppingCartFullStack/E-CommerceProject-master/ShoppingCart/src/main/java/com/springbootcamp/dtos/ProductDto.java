package com.springbootcamp.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ProductDto extends BaseDto{

    @NotEmpty
    @NotNull
    private String name;

    private String description;

    @NotEmpty
    @NotNull
    private String brand;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private Boolean isActive;
    private Boolean isDeleted;
    @NotNull
    @NotEmpty
    private Long categoryId;
    private CategoryDto categoryDto;

    private Set<ProductVariationDto> productVariationDtoSet;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Set<ProductVariationDto> getProductVariationDtoSet() {
        return productVariationDtoSet;
    }

    public void setProductVariationDtoSet(Set<ProductVariationDto> productVariationDtoSet) {
        this.productVariationDtoSet = productVariationDtoSet;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", isActive=" + isActive +
                ", categoryId=" + categoryId +
                ", categoryDto=" + categoryDto +
                '}';
    }
}
