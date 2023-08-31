package com.Auton.gibg.entity.order;


import com.Auton.gibg.entity.product.product_entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private product_entity product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "amount")
    private int amount;

    @Column(name = "createAt")
    private Date createAt;

    // Constructor
    public OrderDetailEntity(product_entity product, int amount) {
        this.product = product;
        this.amount = amount;
        this.createAt = new Date(); // Set current date
    }
}
