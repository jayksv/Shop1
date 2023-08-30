package com.Auton.gibg.repository.shop;

import com.Auton.gibg.entity.shop.shop_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shop_repository extends JpaRepository<shop_entity,Long> {
}
