package com.Auton.gibg.repository.product;

import com.Auton.gibg.controller.product.product_image_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface product_image_repository extends JpaRepository<product_image_entity,Long> {
}
