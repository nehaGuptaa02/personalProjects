//package com.springbootcamp.models;
//
//import javax.persistence.*;
//import java.util.Map;
//
//@Entity
//@Table(name = "ORDER_CART")
//public class OrderCart extends DomainBase {
//    @Column(name = "QUANTITY")
//    private Integer quantity;
//    @Column(name = "PRICE")
//    private Long price;
//    @Column(name="ORDER_PRODUCT_ID")
//    private String orderProductId;
//    @Column(name="IMAGE_NAME")
//    private String imageName;
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Long getPrice() {
//        return price;
//    }
//
//    public void setPrice(Long price) {
//        this.price = price;
//    }
//
//    public String getOrderProductId() {
//        return orderProductId;
//    }
//
//    public void setOrderProductId(String orderProductId) {
//        this.orderProductId = orderProductId;
//    }
//
//    public String getImageName() {
//        return imageName;
//    }
//
//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }
//
//    @Override
//    public String toString() {
//        return "orderCart{" +
//                "quantity=" + quantity +
//                ", price=" + price +
//                ", orderProductId=" + orderProductId +
//                ", imageName='" + imageName + '\'' +
//                '}';
//    }
//}
