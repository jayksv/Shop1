package com.Auton.gibg.repository.product;

import com.Auton.gibg.controller.product.color_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface color_repository extends JpaRepository<color_entity,Long> {
}
