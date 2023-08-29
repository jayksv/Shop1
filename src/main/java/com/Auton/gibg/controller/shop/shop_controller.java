package com.Auton.gibg.controller.shop;


import com.Auton.gibg.middleware.authToken;
import com.Auton.gibg.repository.shop.shop_repostory;
import com.Auton.gibg.response.ResponseWrapper;
import com.Auton.gibg.response.shopAllDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopowner")
public class shop_controller {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public shop_controller(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @GetMapping("/status/all")
//    public ResponseEntity<List<ShopStatusDTO>> getAllShopStatus() {
//        try {
//            String sql = "SELECT `status_id`, `status_name`, `status_description` FROM `shop_status`";
//            List<ShopStatusDTO> shopStatusList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
//                ShopStatusDTO statusDTO = new ShopStatusDTO();
//                statusDTO.setStatusId(resultSet.getLong("status_id"));
//                statusDTO.setStatusName(resultSet.getString("status_name"));
//                statusDTO.setStatusDescription(resultSet.getString("status_description"));
//                return statusDTO;
//            });
//
//            if (shopStatusList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            return new ResponseEntity<>(shopStatusList, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/update/status/{shopId}")
//    public ResponseEntity<String> updateShopStatus(@PathVariable Long shopId, @PathVariable String newStatus) {
//        try {
//            String currentStatus = jdbcTemplate.queryForObject("SELECT `status` FROM `tb_shop` WHERE `shop_id` = ?", String.class, shopId);
//
//            if (currentStatus == null) {
//                return new ResponseEntity<>("Shop not found.", HttpStatus.NOT_FOUND);
//            }
//
//            if (currentStatus.equals("approved") && !newStatus.equals("approved")) {
//                return new ResponseEntity<>("Cannot update status for an approved shop.", HttpStatus.BAD_REQUEST);
//            }
//
//            if (currentStatus.equals("pending") && newStatus.equals("approved")) {
//                return new ResponseEntity<>("Cannot approve a pending shop.", HttpStatus.BAD_REQUEST);
//            }
//
//            String sql = "UPDATE `tb_shop` SET `status` = ? WHERE `shop_id` = ?";
//            int affectedRows = jdbcTemplate.update(sql, newStatus, shopId);
//
//            if (affectedRows > 0) {
//                return new ResponseEntity<>("Shop status updated successfully.", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Shop not found or status update failed.", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Error updating shop status.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
private final JdbcTemplate jdbcTemplate;

    private final authToken authService;
    @Autowired
    private shop_repostory shopRepostory;
    public shop_controller(JdbcTemplate jdbcTemplate,  authToken authService) {
        this.jdbcTemplate = jdbcTemplate;
        this.authService = authService;
    }

    // find all shop repositories

    @GetMapping("/shop/all")
    public ResponseEntity<ResponseWrapper<List<shopAllDTO>>> getAllRoles(@RequestHeader("Authorization") String authorizationHeader) {
        try {


            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<List<shopAllDTO>> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            String sql = "SELECT s.shop_name, s.shop_status_id,s.shop_type_id,s.shop_id, s.street_address, s.city, s.state, s.postal_code, s.country, s.latitude, s.longitude, u.first_name AS owner_name, ss.status_name, st.type_name, s.shop_image, s.monday_open, s.monday_close, s.tuesday_open, s.tuesday_close, s.wednesday_open, s.wednesday_close, s.thursday_open, s.thursday_close, s.friday_open, s.friday_close, s.saturday_open, s.saturday_close, s.sunday_open, s.sunday_close, s.created_at FROM tb_shop s JOIN tb_users u ON s.shop_id = u.shop_id JOIN shop_status ss ON s.shop_status_id = ss.status_id JOIN tb_shop_types st ON s.shop_type_id = st.type_id";
            List<shopAllDTO> roles = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                shopAllDTO shop = new shopAllDTO();
                shop.setShop_id(resultSet.getLong("shop_id"));
                shop.setShop_name(resultSet.getString("shop_name"));
                shop.setStreet_address(resultSet.getString("street_address"));
                shop.setCity(resultSet.getString("city"));
                shop.setState(resultSet.getString("state"));
                shop.setPostal_code(resultSet.getString("postal_code"));
                shop.setCountry(resultSet.getString("country"));
                shop.setLatitude(resultSet.getBigDecimal("latitude"));
                shop.setLongitude(resultSet.getBigDecimal("longitude"));
                shop.setStatus_name(resultSet.getString("status_name"));
                shop.setType_name(resultSet.getString("type_name"));
                shop.setShop_image(resultSet.getString("shop_image"));
                shop.setMonday_open(resultSet.getTime("monday_open"));
                shop.setMonday_close(resultSet.getTime("monday_close"));
                shop.setTuesday_open(resultSet.getTime("tuesday_open"));
                shop.setTuesday_close(resultSet.getTime("tuesday_close"));
                shop.setWednesday_open(resultSet.getTime("wednesday_open"));
                shop.setWednesday_close(resultSet.getTime("wednesday_close"));
                shop.setThursday_open(resultSet.getTime("thursday_open"));
                shop.setThursday_close(resultSet.getTime("thursday_close"));
                shop.setFriday_open(resultSet.getTime("friday_open"));
                shop.setFriday_close(resultSet.getTime("friday_close"));
                shop.setSaturday_open(resultSet.getTime("saturday_open"));
                shop.setSaturday_close(resultSet.getTime("saturday_close"));
                shop.setSunday_open(resultSet.getTime("sunday_open"));
                shop.setSunday_close(resultSet.getTime("sunday_close"));
                shop.setShop_owner(resultSet.getString("owner_name"));
                shop.setShop_type_id(resultSet.getLong("shop_type_id"));
                shop.setShop_status_id(resultSet.getLong("shop_status_id"));
                return shop;
            });

            ResponseWrapper<List<shopAllDTO>> responseWrapper = new ResponseWrapper<>("shops retrieved successfully.", roles);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving roles.";
            ResponseWrapper<List<shopAllDTO>> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/shop/findById/{shopId}")
    public ResponseEntity<ResponseWrapper<shopAllDTO>> getShopById(@PathVariable Long shopId, @RequestHeader("Authorization") String authorizationHeader) {
        try {

            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<shopAllDTO> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            String sql = "SELECT s.shop_name, s.shop_status_id, s.shop_type_id, s.shop_id, s.street_address, s.city, s.state, s.postal_code, s.country, s.latitude, s.longitude, u.first_name AS owner_name, ss.status_name, st.type_name, s.shop_image, s.monday_open, s.monday_close, s.tuesday_open, s.tuesday_close, s.wednesday_open, s.wednesday_close, s.thursday_open, s.thursday_close, s.friday_open, s.friday_close, s.saturday_open, s.saturday_close, s.sunday_open, s.sunday_close, s.created_at FROM tb_shop s JOIN tb_users u ON s.shop_id = u.shop_id JOIN shop_status ss ON s.shop_status_id = ss.status_id JOIN tb_shop_types st ON s.shop_type_id = st.type_id WHERE s.shop_id = ?";

            shopAllDTO shop = jdbcTemplate.queryForObject(sql, new Object[]{shopId}, (resultSet, rowNum) -> {
                shopAllDTO shopDto = new shopAllDTO();
                shopDto.setShop_id(resultSet.getLong("shop_id"));
                shopDto.setShop_name(resultSet.getString("shop_name"));
                shopDto.setStreet_address(resultSet.getString("street_address"));
                shopDto.setCity(resultSet.getString("city"));
                shopDto.setState(resultSet.getString("state"));
                shopDto.setPostal_code(resultSet.getString("postal_code"));
                shopDto.setCountry(resultSet.getString("country"));
                shopDto.setLatitude(resultSet.getBigDecimal("latitude"));
                shopDto.setLongitude(resultSet.getBigDecimal("longitude"));
                shopDto.setStatus_name(resultSet.getString("status_name"));
                shopDto.setType_name(resultSet.getString("type_name"));
                shopDto.setShop_image(resultSet.getString("shop_image"));
                shopDto.setMonday_open(resultSet.getTime("monday_open"));
                shopDto.setMonday_close(resultSet.getTime("monday_close"));
                shopDto.setTuesday_open(resultSet.getTime("tuesday_open"));
                shopDto.setTuesday_close(resultSet.getTime("tuesday_close"));
                shopDto.setWednesday_open(resultSet.getTime("wednesday_open"));
                shopDto.setWednesday_close(resultSet.getTime("wednesday_close"));
                shopDto.setThursday_open(resultSet.getTime("thursday_open"));
                shopDto.setThursday_close(resultSet.getTime("thursday_close"));
                shopDto.setFriday_open(resultSet.getTime("friday_open"));
                shopDto.setFriday_close(resultSet.getTime("friday_close"));
                shopDto.setSaturday_open(resultSet.getTime("saturday_open"));
                shopDto.setSaturday_close(resultSet.getTime("saturday_close"));
                shopDto.setSunday_open(resultSet.getTime("sunday_open"));
                shopDto.setSunday_close(resultSet.getTime("sunday_close"));
                shopDto.setShop_owner(resultSet.getString("owner_name"));
                shopDto.setShop_type_id(resultSet.getLong("shop_type_id"));
                shopDto.setShop_status_id(resultSet.getLong("shop_status_id"));
                return shopDto;
            });

            if (shop != null) {
                ResponseWrapper<shopAllDTO> responseWrapper = new ResponseWrapper<>("Shop retrieved successfully.", shop);
                return ResponseEntity.ok(responseWrapper);
            } else {
                ResponseWrapper<shopAllDTO> notFoundResponse = new ResponseWrapper<>("Shop not found.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving the shop.";
            ResponseWrapper<shopAllDTO> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/shop/status/{statusId}")
    public ResponseEntity<ResponseWrapper<List<shopAllDTO>>> getShopsByStatus(@PathVariable Long statusId, @RequestHeader("Authorization") String authorizationHeader) {
        try {

            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<List<shopAllDTO>> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            String sql = "SELECT s.shop_name, s.shop_status_id, s.shop_type_id, s.shop_id, s.street_address, s.city, s.state, s.postal_code, s.country, s.latitude, s.longitude, u.first_name AS owner_name, ss.status_name, st.type_name, s.shop_image, s.monday_open, s.monday_close, s.tuesday_open, s.tuesday_close, s.wednesday_open, s.wednesday_close, s.thursday_open, s.thursday_close, s.friday_open, s.friday_close, s.saturday_open, s.saturday_close, s.sunday_open, s.sunday_close, s.created_at FROM tb_shop s JOIN tb_users u ON s.shop_id = u.shop_id JOIN shop_status ss ON s.shop_status_id = ss.status_id JOIN tb_shop_types st ON s.shop_type_id = st.type_id WHERE s.shop_status_id = ?";

            List<shopAllDTO> shops = jdbcTemplate.query(sql, new Object[]{statusId}, (resultSet, rowNum) -> {
                shopAllDTO shopDto = new shopAllDTO();
                shopDto.setShop_id(resultSet.getLong("shop_id"));
                shopDto.setShop_name(resultSet.getString("shop_name"));
                shopDto.setStreet_address(resultSet.getString("street_address"));
                shopDto.setCity(resultSet.getString("city"));
                shopDto.setState(resultSet.getString("state"));
                shopDto.setPostal_code(resultSet.getString("postal_code"));
                shopDto.setCountry(resultSet.getString("country"));
                shopDto.setLatitude(resultSet.getBigDecimal("latitude"));
                shopDto.setLongitude(resultSet.getBigDecimal("longitude"));
                shopDto.setStatus_name(resultSet.getString("status_name"));
                shopDto.setType_name(resultSet.getString("type_name"));
                shopDto.setShop_image(resultSet.getString("shop_image"));
                shopDto.setMonday_open(resultSet.getTime("monday_open"));
                shopDto.setMonday_close(resultSet.getTime("monday_close"));
                shopDto.setTuesday_open(resultSet.getTime("tuesday_open"));
                shopDto.setTuesday_close(resultSet.getTime("tuesday_close"));
                shopDto.setWednesday_open(resultSet.getTime("wednesday_open"));
                shopDto.setWednesday_close(resultSet.getTime("wednesday_close"));
                shopDto.setThursday_open(resultSet.getTime("thursday_open"));
                shopDto.setThursday_close(resultSet.getTime("thursday_close"));
                shopDto.setFriday_open(resultSet.getTime("friday_open"));
                shopDto.setFriday_close(resultSet.getTime("friday_close"));
                shopDto.setSaturday_open(resultSet.getTime("saturday_open"));
                shopDto.setSaturday_close(resultSet.getTime("saturday_close"));
                shopDto.setSunday_open(resultSet.getTime("sunday_open"));
                shopDto.setSunday_close(resultSet.getTime("sunday_close"));
                shopDto.setShop_owner(resultSet.getString("owner_name"));
                shopDto.setShop_type_id(resultSet.getLong("shop_type_id"));
                shopDto.setShop_status_id(resultSet.getLong("shop_status_id"));
                return shopDto;

            });

            if (!shops.isEmpty()) {
                ResponseWrapper<List<shopAllDTO>> responseWrapper = new ResponseWrapper<>("Shops retrieved successfully.", shops);
                return ResponseEntity.ok(responseWrapper);
            } else {
                ResponseWrapper<List<shopAllDTO>> notFoundResponse = new ResponseWrapper<>("No shops found for the given status ID.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving the shops.";
            ResponseWrapper<List<shopAllDTO>> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }


    }

}