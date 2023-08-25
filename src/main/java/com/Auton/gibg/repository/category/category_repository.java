package com.Auton.gibg.repository.category;

import com.Auton.gibg.entity.category.category_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface category_repository extends JpaRepository<category_entity, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM tb_categories WHERE category_name = :category_name", nativeQuery = true)
    boolean existsByCategoryName(String category_name);
//    category_entity findById(long id);
}
