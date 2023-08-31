package com.Auton.gibg.repository.shop;

import com.Auton.gibg.controller.shop.shop_image_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shop_image_repository extends JpaRepository<shop_image_entity,Long> {
}
