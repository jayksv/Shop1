package com.Auton.gibg.response.product;

import com.Auton.gibg.controller.product.color_entity;
import com.Auton.gibg.controller.product.product_image_entity;
import com.Auton.gibg.controller.product.size_entity;
import com.Auton.gibg.entity.product.product_entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithColorsSizesAndImagesResponse {
    private String message;
    private product_entity product;
    private List<color_entity> colors;
    private List<size_entity> sizes;
    private List<product_image_entity> productImages;
}
