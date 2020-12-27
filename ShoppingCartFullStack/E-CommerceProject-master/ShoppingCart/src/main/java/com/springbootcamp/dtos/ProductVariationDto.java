package com.springbootcamp.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class ProductVariationDto extends BaseDto {
    private Integer quantityAvailable;
    private Long price;

    //@NotNull it is mandatory param
    private String primaryImageName;

    private Boolean isActive;


    //@NotNull
    private Map<String, Object> metadata;

    private ProductDto productDto;
    private List<String> secondaryImages;

    @NotNull
    private Long productId;

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public List<String> getSecondaryImages() {
        return secondaryImages;
    }

    public void setSecondaryImages(List<String> secondaryImages) {
        this.secondaryImages = secondaryImages;
    }
}
