package com.Auton.gibg.entity.shopstatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "status_description")
    private String status_description;

}
