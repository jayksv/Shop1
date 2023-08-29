package com.Auton.gibg.entity.shopstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    private Long shopId;
    private String shopName;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private int shopType;
    private String status_name;
    private Long shop_status_id;
    private String shopImage;
    private String mondayOpen;
    private String mondayClose;
    private String tuesdayOpen;
    private String tuesdayClose;
    private String wednesdayOpen;
    private String wednesdayClose;
    private String thursdayOpen;
    private String thursdayClose;
    private String fridayOpen;
    private String fridayClose;
    private String saturdayOpen;
    private String saturdayClose;
    private String sundayOpen;
    private String sundayClose;

}
