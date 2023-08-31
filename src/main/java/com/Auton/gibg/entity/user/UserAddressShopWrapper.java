package com.Auton.gibg.entity.user;
import com.Auton.gibg.controller.product.color_entity;
import com.Auton.gibg.controller.product.product_image_entity;
import com.Auton.gibg.controller.product.size_entity;
import com.Auton.gibg.controller.shop.shop_amenitie_entity;
import com.Auton.gibg.controller.shop.shop_image_entity;
import com.Auton.gibg.controller.shop.shop_service_entity;
import com.Auton.gibg.entity.address.address_entity;
import com.Auton.gibg.entity.shop.shop_entity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserAddressShopWrapper {
    private user_entity user;
    private address_entity userAddress;
    private shop_entity userShop;
    private List<shop_amenitie_entity> shop_amenities;
    private List<shop_service_entity> shop_service;
    private List<shop_image_entity> shop_images;

}
