package com.springbootcamp.models;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_TABLE")
public class Order extends DomainBase {
    @Column(name = "AMOUNT_PAID")
    private Long amountPaid;
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderProduct orderProduct;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address addresses;

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

   public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }


}
