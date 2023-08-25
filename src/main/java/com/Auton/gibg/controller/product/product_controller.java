package com.Auton.gibg.controller.product;



import com.Auton.gibg.entity.product.product_entity;
import com.Auton.gibg.response.ResponseWrapper;
import com.Auton.gibg.response.usersDTO.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.Auton.gibg.repository.product.product_repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shopowner")
public class product_controller {
    @Value("${jwt_secret}")
    private String jwt_secret;
    private final JdbcTemplate jdbcTemplate;

    // เมธอดสำหรับตรวจสอบว่าค่าที่รับเข้ามาเป็นตัวเลขที่ถูกต้องหรือไม่
    private boolean isValidNumberFormat(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Autowired
    private product_repository ProductRepository;

    public product_controller(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("/product/all")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getProductOnly(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
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
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Token has expired.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Extract necessary claims (you can add more as needed)
            Long authenticatedUserId = claims.get("user_id", Long.class);
            String role = claims.get("role_name", String.class);

            // Check if the user has the appropriate role to perform this action (e.g., shop owner)
            if (!"shop owner".equalsIgnoreCase(role)) {
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            // Query the database to retrieve product data
            String sql = "SELECT tb_products.product_id,\n" +
                    "tb_products.description,\n" +
                    "tb_products.product_name,\n" +
                    "tb_products.price,\n" +
                    "tb_products.stock_quantity,\n" +
                    "tb_products.product_image,\n" +
                    "tb_products.product_any_image,\n" +
                    "CONCAT(tb_users.first_name, ' ', tb_users.last_name) AS full_name,\n" +
                    "tb_shop.shop_id,\n" +
                    "tb_categories.category_name \n" +
                    "FROM tb_products \n" +
                    "JOIN tb_users ON tb_products.user_id = tb_users.user_id\n" +
                    "JOIN tb_shop ON tb_products.shope_id = tb_shop.shop_id\n" +
                    "JOIN tb_categories ON tb_products.product_id = tb_categories.category_name;";
            List<ProductDTO> productDTOList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProduct_id(resultSet.getLong("product_id"));
                productDTO.setDescription(resultSet.getString("description"));
                productDTO.setProduct_name(resultSet.getString("product_name"));
                productDTO.setPrice(resultSet.getBigDecimal("price"));
                productDTO.setStock_quantity(resultSet.getInt("stock_quantity"));
                productDTO.setUser_id(resultSet.getLong("user_id"));
                productDTO.setShope_id(resultSet.getLong("shope_id"));
                productDTO.setProduct_image(resultSet.getString("product_image"));
                productDTO.setProduct_any_image(resultSet.getString("product_any_image"));
                productDTO.setCategory_name(resultSet.getString("category_name"));
                productDTO.setCreateBy(resultSet.getString("first_name"));

                return productDTO;
            });

            ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Product data retrieved successfully.", productDTOList);
            return ResponseEntity.ok(responseWrapper);
        } catch (JwtException e) {
            // Token is invalid or has expired
            ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving product data.";
            ResponseWrapper<List<ProductDTO>> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/product/finById")
    public ResponseEntity<ResponseWrapper<List<ProductDTO>>> getProductFinById(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
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
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Token has expired.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Extract necessary claims (you can add more as needed)
            Long authenticatedUserId = claims.get("user_id", Long.class);
            String role = claims.get("role_name", String.class);

            // Check if the user has the appropriate role to perform this action (e.g., shop owner)
            if (!"shop owner".equalsIgnoreCase(role)) {
                ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            // Query the database to retrieve product data
            String sql = "SELECT tb_products.product_id,\n" +
                    "tb_products.description,\n" +
                    "tb_products.product_name,\n" +
                    "tb_products.price,\n" +
                    "tb_products.stock_quantity,\n" +
                    "tb_products.product_image,\n" +
                    "tb_products.product_any_image,\n" +
                    "CONCAT(tb_users.first_name, ' ', tb_users.last_name) AS full_name,\n" +
                    "tb_shop.shop_id,\n" +
                    "tb_categories.category_name \n" +
                    "FROM tb_products \n" +
                    "JOIN tb_users ON tb_products.user_id = tb_users.user_id\n" +
                    "JOIN tb_shop ON tb_products.shope_id = tb_shop.shop_id\n" +
                    "JOIN tb_categories ON tb_products.product_id = tb_categories.category_name";

            List<ProductDTO> productDTOList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProduct_id(resultSet.getLong("product_id"));
                productDTO.setDescription(resultSet.getString("description"));
                productDTO.setProduct_name(resultSet.getString("product_name"));
                productDTO.setPrice(resultSet.getBigDecimal("price"));
                productDTO.setStock_quantity(resultSet.getInt("stock_quantity"));
                productDTO.setUser_id(resultSet.getLong("user_id"));
                productDTO.setShope_id(resultSet.getLong("shope_id"));
                productDTO.setProduct_image(resultSet.getString("product_image"));
                productDTO.setProduct_any_image(resultSet.getString("product_any_image"));
                productDTO.setCreateBy(resultSet.getString("creator_name"));
                productDTO.setCategory_name(resultSet.getString("category_name"));

                return productDTO;
            });

            ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Product data retrieved successfully.", productDTOList);
            return ResponseEntity.ok(responseWrapper);
        } catch (JwtException e) {
            // Token is invalid or has expired
            ResponseWrapper<List<ProductDTO>> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
        } catch (Exception e) {
            String errorMessage = "An error occurred while retrieving product data.";
            ResponseWrapper<List<ProductDTO>> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/shop/add_product")
    public ResponseEntity<ResponseWrapper<product_entity>> addProductToShop(@RequestBody product_entity product, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Verify the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret)
                    .parseClaimsJws(token)
                    .getBody();

            // Check token expiration
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Token has expired.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Extract necessary claims (you can add more as needed)
            Long userId = claims.get("user_id", Long.class);
            String role = claims.get("role_name", String.class);

            // Check if the user has the appropriate role to perform this action (e.g., shop owner)
            if (!"shop owner".equalsIgnoreCase(role)) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }
            // Check if the shopType object is null
            if (product.getShope_id() == null) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Shop ID data is missing.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
            }

            // Check if the product price is a number
            if (!isValidPrice(product.getPrice())) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Product price is not a valid number.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseWrapper);
            }



            // บันทึกข้อมูลสินค้าลงในฐานข้อมูลหรือระบบจัดเก็บข้อมูล
            product_entity addedProduct = ProductRepository.save(product);

            ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Product added successfully", addedProduct);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while adding the product.";
            ResponseWrapper<product_entity> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    private boolean isValidPrice(BigDecimal price) {
        // You can add additional checks here if needed
        return price != null && price.compareTo(BigDecimal.ZERO) >= 0;
    }
    @PutMapping("/shop/update_product/{productId}")
    public ResponseEntity<ResponseWrapper<product_entity>> updateProduct(@PathVariable Long productId, @RequestBody product_entity updatedProduct, @RequestHeader ("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Verify the token from the Authorization header
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret)
                    .parseClaimsJws(token)
                    .getBody();

            // Check if the user has the appropriate role to perform this action (e.g., shop owner)
            String role = claims.get("role_name", String.class);
            if (!"shop owner".equalsIgnoreCase(role)) {
                ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            // ดึงข้อมูลสินค้าจากฐานข้อมูล
            product_entity existingProduct = ProductRepository.findById(productId).orElse(null);

            if (existingProduct == null) {
                ResponseWrapper<product_entity> notFoundResponse = new ResponseWrapper<>("Product not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }

            // อัพเดตข้อมูลสินค้าที่ได้รับมาจาก Request ลงในสินค้าที่มีอยู่ในฐานข้อมูล
            existingProduct.setProduct_name(updatedProduct.getProduct_name());
            existingProduct.setDescription(updatedProduct.getDescription());
            // ... อัพเดตข้อมูลอื่น ๆ ตามต้องการ ...

            // บันทึกการเปลี่ยนแปลงลงในฐานข้อมูล
            ProductRepository.save(existingProduct);

            ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Product updated successfully", existingProduct);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while updating the product.";
            ResponseWrapper<product_entity> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
