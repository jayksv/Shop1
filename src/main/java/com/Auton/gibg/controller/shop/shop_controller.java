package com.Auton.gibg.controller.shop;


import com.Auton.gibg.entity.address.address_entity;
import com.Auton.gibg.entity.shop.shop_entity;
import com.Auton.gibg.entity.user.UserAddressShopWrapper;
import com.Auton.gibg.entity.user.user_entity;
import com.Auton.gibg.middleware.authToken;
import com.Auton.gibg.repository.address.address_repository;
import com.Auton.gibg.repository.shop.shop_repository;
import com.Auton.gibg.repository.user.user_repository;
import com.Auton.gibg.response.ResponseWrapper;
import com.Auton.gibg.response.shopAllDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shopowner")
public class shop_controller {

    @Value("${jwt_secret}")
    private String jwt_secret;
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
    private shop_repository shop_repository;
    @Autowired
    private user_repository user_repository;
    @Autowired
    private address_repository address_repository;
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
    @PutMapping("/user/update_shopowner/{userId}")
    public ResponseEntity<ResponseWrapper<user_entity>> updateUserWithShopOwner(
            @PathVariable Long userId,
            @RequestBody UserAddressShopWrapper userAddressShopWrapper,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Verify the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret) // Replace with your secret key
                    .parseClaimsJws(token)
                    .getBody();

            // Check token expiration
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("Token has expired.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Extract necessary claims (you can add more as needed)
            Long authenticatedUserId = claims.get("user_id", Long.class);
            String role = claims.get("role_name", String.class);

            // Check if the authenticated user has the appropriate role to perform this action (e.g., admin)
            if (!"shop owner".equalsIgnoreCase(role)) {

                ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            user_entity updatedUser = userAddressShopWrapper.getUser();
            address_entity updatedAddress = userAddressShopWrapper.getUserAddress();
            shop_entity updatedShop = userAddressShopWrapper.getUserShop();

            // Find the existing user by ID
            Optional<user_entity> userOptional = user_repository.findById(userId);

            if (userOptional.isEmpty()) {
                ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("User not found.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseWrapper);
            }

            Optional<address_entity> existingAddress = address_repository.findById(userOptional.get().getAddress_id());
            Optional<shop_entity> existingShop = shop_repository.findById(userOptional.get().getShop_id());

            user_entity existingUser = userOptional.get();
            address_entity addressToUpdate = existingAddress.get();
            shop_entity shopToUpdate = existingShop.get();


            // Update user's information
            user_entity userToUpdate = userOptional.get();
//            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setPassword(updatedUser.getPassword());
            userToUpdate.setFirst_name(updatedUser.getFirst_name());
            userToUpdate.setLast_name(updatedUser.getLast_name());
            userToUpdate.setBirthdate(updatedUser.getBirthdate());
            userToUpdate.setGender(updatedUser.getGender());
            userToUpdate.setProfile_picture(updatedUser.getProfile_picture());
            userToUpdate.setBio(updatedUser.getBio());
            userToUpdate.setRole_id(updatedUser.getRole_id());

            // Update shop's information
            shop_entity shopOwnToUpdate = existingShop.get();
            shopOwnToUpdate.setShopName(updatedShop.getShopName());
            shopOwnToUpdate.setStreetAddress(updatedShop.getStreetAddress());
            shopOwnToUpdate.setCity(updatedShop.getCity());
            shopOwnToUpdate.setState(updatedShop.getState());
            shopOwnToUpdate.setPostalCode(updatedShop.getPostalCode());
            shopOwnToUpdate.setCountry(updatedShop.getCountry());
            shopOwnToUpdate.setLongitude(updatedShop.getLongitude());
            shopOwnToUpdate.setLatitude(updatedShop.getLatitude());
            shopOwnToUpdate.setMondayOpen(updatedShop.getMondayOpen());
            shopOwnToUpdate.setMondayClose(updatedShop.getMondayClose());
            shopOwnToUpdate.setTuesday_open(updatedShop.getTuesday_open());
            shopOwnToUpdate.setTuesday_close(updatedShop.getTuesday_close());
            shopOwnToUpdate.setWednesday_open(updatedShop.getWednesday_open());
            shopOwnToUpdate.setWednesday_close(updatedShop.getWednesday_close());
            shopOwnToUpdate.setThursday_open(updatedShop.getThursday_open());
            shopOwnToUpdate.setThursday_close(updatedShop.getThursday_close());
            shopOwnToUpdate.setFriday_open(updatedShop.getFriday_open());
            shopOwnToUpdate.setFriday_close(updatedShop.getFriday_close());
            shopOwnToUpdate.setSaturday_open(updatedShop.getSaturday_open());
            shopOwnToUpdate.setSaturday_close(updatedShop.getSaturday_close());
            shopOwnToUpdate.setSunday_open(updatedShop.getSunday_open());
            shopOwnToUpdate.setSunday_close(updatedShop.getSunday_close());
            shopToUpdate.setShop_type_id(updatedShop.getShop_type_id());


            // Update address information
            address_entity addressToUpdates = existingAddress.get();
            addressToUpdates.setStreetAddress(updatedAddress.getStreetAddress());
            addressToUpdates.setState(updatedAddress.getState());
            addressToUpdates.setPostalCode(updatedAddress.getPostalCode());
            addressToUpdates.setCountry(updatedAddress.getCountry());
            addressToUpdates.setLatitude(updatedAddress.getLatitude());
            addressToUpdates.setLongitude(updatedAddress.getLongitude());

            // Save the updated entities
            user_repository.save(existingUser);
            shop_repository.save(shopToUpdate);
            address_repository.save(addressToUpdate);


            ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("User information updated successfully.", null);
            return ResponseEntity.ok(responseWrapper);

        } catch (JwtException e) {
            // Token is invalid or has expired
            ResponseWrapper<user_entity> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while updating the user information.";
            ResponseWrapper<user_entity> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PutMapping("/shop/update")
    public ResponseEntity<ResponseWrapper<Void>> updateShopById(

            @RequestBody shop_entity updatedShop,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<Void> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Verify the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret) // Replace with your secret key
                    .parseClaimsJws(token)
                    .getBody();



            // Extract necessary claims (you can add more as needed)
            Long authenticatedshopId = claims.get("shop_id", Long.class);
            System.out.println(updatedShop.getShop_status_id());

            // Check if the shop status allows updates
            Long shopStatusId = updatedShop.getShop_status_id();
            if (shopStatusId.equals(1L)) {
                ResponseWrapper<Void> statusResponse = new ResponseWrapper<>("Cannot update shop with pending status.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(statusResponse);
            }

            // Prepare the SQL query to update all columns of the shop
            String updateShopSql = "UPDATE tb_shop SET "
                    + "shop_name = ?, street_address = ?, city = ?, state = ?, "
                    + "postal_code = ?, country = ?, latitude = ?, longitude = ?, "
                    + "shop_type_id = ?, shop_image = ?, shop_status_id = ?, "
                    + "monday_open = ?, monday_close = ?, tuesday_open = ?, tuesday_close = ?, "
                    + "wednesday_open = ?, wednesday_close = ?, thursday_open = ?, thursday_close = ?, "
                    + "friday_open = ?, friday_close = ?, saturday_open = ?, saturday_close = ?, "
                    + "sunday_open = ?, sunday_close = ?, created_at = ? "
                    + "WHERE shop_id = ?";
            if (shopStatusId.equals(3L)) {
                updatedShop.setShop_status_id(1L);
            }
            int rowsAffected = jdbcTemplate.update(
                    updateShopSql,
                    updatedShop.getShopName(),
                    updatedShop.getStreetAddress(),
                    updatedShop.getCity(),
                    updatedShop.getState(),
                    updatedShop.getPostalCode(),
                    updatedShop.getCountry(),
                    updatedShop.getLatitude(),
                    updatedShop.getLongitude(),
                    updatedShop.getShop_type_id(),
                    updatedShop.getShop_image(),
                   updatedShop.getShop_status_id(),
                    updatedShop.getMondayOpen(),
                    updatedShop.getMondayClose(),
                    updatedShop.getThursday_open(),
                    updatedShop.getThursday_close(),
                    updatedShop.getWednesday_open(),
                    updatedShop.getWednesday_close(),
                    updatedShop.getTuesday_open(),
                    updatedShop.getTuesday_close(),
                    updatedShop.getFriday_open(),
                    updatedShop.getFriday_close(),
                    updatedShop.getSaturday_open(),
                    updatedShop.getSaturday_close(),
                    updatedShop.getSunday_open(),
                    updatedShop.getSunday_close(),
                    updatedShop.getCreated_at(),
                    authenticatedshopId
            );


            if (rowsAffected > 0) {
                ResponseWrapper<Void> responseWrapper = new ResponseWrapper<>("Shop updated successfully.", null);
                return ResponseEntity.ok(responseWrapper);
            } else {
                ResponseWrapper<Void> notFoundResponse = new ResponseWrapper<>("Shop not found or not updated.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while updating the shop.";
            ResponseWrapper<Void> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



}