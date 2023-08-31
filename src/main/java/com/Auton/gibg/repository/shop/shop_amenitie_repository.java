package com.Auton.gibg.repository.shop;

import com.Auton.gibg.controller.shop.shop_amenitie_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shop_amenitie_repository extends JpaRepository<shop_amenitie_entity,Long> {

}
