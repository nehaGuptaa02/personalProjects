package com.springbootcamp.models;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "PRODUCT_VARIATION")
public class ProductVariation extends DomainBase {

    @Column(name = "QUANTITY_AVAILABLE")
    private Integer quantityAvailable;
    @Column(name = "PRICE")
    private Long price;
    @Column(name = "PRIMARY_IMAGE_NAME")
    private String primaryImageName;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
//
//    private String metadataJSON;
    @Column(name = "METADATA")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> metadata;

//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public void serializeAttributes() throws JsonProcessingException {
//        this.metadataJSON = objectMapper.writeValueAsString(metadata);
//    }
//
//    public void deserializeAttributes() throws IOException {
//        this.metadata = objectMapper.readValue(metadataJSON, Map.class);
//    }

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


    @OneToOne(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private Cart cart;
    @OneToOne(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private OrderProduct orderProduct;

    public ProductVariation() {

    }

    public ProductVariation(Integer quantityAvailable, Long price) {
        this.quantityAvailable = quantityAvailable;
        this.price = price;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Boolean getActive() {
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

    @Override
    public String toString() {
        return "ProductVariation{" +
                "quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", primaryImageName='" + primaryImageName + '\'' +
                ", isActive=" + isActive + '\'' +
                ", metadata=" + metadata +
                ", product=" + product +
                ", cart=" + cart +
                ", orderProduct=" + orderProduct +
                '}';
    }
}
