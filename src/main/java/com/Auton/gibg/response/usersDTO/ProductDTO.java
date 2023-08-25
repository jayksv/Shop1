package com.Auton.gibg.response.usersDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long product_id;
    private String product_name;
    private String description;
    private BigDecimal price;
    private Integer stock_quantity;
    private Long user_id;
    private Long shope_id;
    private String product_image;
    private String product_any_image;
    private String createBy;
    private String category_name;
}
