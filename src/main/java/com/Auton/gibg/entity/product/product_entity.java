package com.Auton.gibg.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class product_entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long product_id;

    @Column(name = "description")
    private String description;

    @Column(name = "product_name", nullable = false)
    private String product_name;

    @JsonProperty("price")
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private int stock_quantity;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "shope_id", nullable = false)
    private Long shope_id;

    @Column(name = "product_image")
    private String product_image;

    @Column(name = "product_any_image")
    private String product_any_image;

    @Column(name = "category_name")
    private String category_name;
}
