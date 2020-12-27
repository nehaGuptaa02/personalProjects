package com.springbootcamp.models;

import com.springbootcamp.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_STATUS")
public class OrderStatus extends DomainBase {

    @Column(name = "FROM_STATUS")
    private Status from_status;
    @Column(name = "TO_STATUS")
    private Status to_status;
    @OneToOne
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct order_product;

    @Column(name = "TRANSITION_NOTES_COMMENTS")
    private String transitionNotesComments;

    public Status getFrom_status() {
        return from_status;
    }

    public void setFrom_status(Status from_status) {
        this.from_status = from_status;
    }

    public Status getTo_status() {
        return to_status;
    }

    public void setTo_status(Status to_status) {
        this.to_status = to_status;
    }

    public OrderProduct getOrder_product() {
        return order_product;
    }

    public void setOrder_product(OrderProduct order_product) {
        this.order_product = order_product;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }
}
