package com.Auton.gibg.repository.product;

import com.Auton.gibg.controller.product.size_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface size_repository extends JpaRepository<size_entity,Long> {
}
