package com.Auton.gibg.response.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long product_id;
    private String description;
    private String product_name;
    private Double price;
    private Long stock_quantity;
    private Long user_id;
    private Long shope_id;
    private Long category_id;
    private String product_image;
    private Date createAt;

    private String create_by;
    private String category_name;
    private String shop_name;
}
