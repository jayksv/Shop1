package com.Auton.gibg.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Auton.gibg.entity.user.user_entity;
import org.springframework.stereotype.Repository;

@Repository
public interface user_repository extends JpaRepository<user_entity, Long> {
    user_entity findByEmail(String email);
}
