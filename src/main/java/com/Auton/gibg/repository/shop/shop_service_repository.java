package com.Auton.gibg.repository.shop;

import com.Auton.gibg.controller.shop.shop_service_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shop_service_repository extends JpaRepository<shop_service_entity,Long> {
}
