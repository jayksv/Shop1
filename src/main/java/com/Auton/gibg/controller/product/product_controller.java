package com.Auton.gibg.controller.product;



import com.Auton.gibg.entity.product.product_entity;
import com.Auton.gibg.entity.user.user_entity;
import com.Auton.gibg.middleware.authToken;
import com.Auton.gibg.repository.product.color_repository;
import com.Auton.gibg.repository.product.product_image_repository;
import com.Auton.gibg.repository.product.size_repository;
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
import com.Auton.gibg.repository.user.user_repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shopowner")
public class product_controller {
    @Value("${jwt_secret}")
    private String jwt_secret;
    private final JdbcTemplate jdbcTemplate;
    private final authToken authService;

    @Autowired
    private color_repository colorRepository;

    @Autowired
    private size_repository sizeRepository;
    @Autowired
    private product_image_repository productImageRepository;

    @Autowired
    private product_repository productRepository;
    @Autowired
    private user_repository userRepository;

    public product_controller(JdbcTemplate jdbcTemplate, authToken authService) {
        this.jdbcTemplate = jdbcTemplate;
        this.authService = authService;
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
            String sql = "SELECT\n" +
                    "    p.product_id,\n" +
                    "    p.product_name,\n" +
                    "    p.description,\n" +
                    "    p.price,\n" +
                    "    p.stock_quantity,\n" +
                    "    p.created_at,\n" +
                    "    p.user_id,\n" +
                    "    p.shope_id,\n" +
                    "    p.product_image,\n" +
                    "    p.product_any_image,\n" +
                    "    p.category_id,\n" +
                    "    u.first_name,\n" +
                    "    c.category_id\n" +
                    "FROM tb_products p\n" +
                    "JOIN tb_users u ON p.user_id = u.user_id\n" +
                    "JOIN tb_categories c ON p.category_id = c.category_id";
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
                productDTO.setCategory_id(resultSet.getString("category_id"));
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
    @GetMapping("/product/finById/{productId}")
    public ResponseEntity<ResponseWrapper<ProductDTO>> getProductFinById(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<ProductDTO> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
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
                ResponseWrapper<ProductDTO> responseWrapper = new ResponseWrapper<>("Token has expired.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Extract necessary claims (you can add more as needed)
            Long authenticatedUserId = claims.get("user_id", Long.class);
            String role = claims.get("role_name", String.class);

            // Check if the user has the appropriate role to perform this action (e.g., shop owner)
            if (!"shop owner".equalsIgnoreCase(role)) {
                ResponseWrapper<ProductDTO> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            // Query the database to retrieve product data
            String sql = "SELECT\n" +
                    "    p.product_id,\n" +
                    "    p.product_name,\n" +
                    "    p.description,\n" +
                    "    p.price,\n" +
                    "    p.stock_quantity,\n" +
                    "    p.created_at,\n" +
                    "    p.user_id,\n" +
                    "    p.shope_id,\n" +
                    "    p.product_image,\n" +
                    "    p.product_any_image,\n" +
                    "    p.category_id,\n" +
                    "    u.first_name,\n" +
                    "    c.category_id\n" +
                    "FROM tb_products p\n" +
                    "JOIN tb_users u ON p.user_id = u.user_id\n" +
                    "JOIN tb_categories c ON p.category_id = c.category_id\n" +
                    "WHERE p.product_id = ?";

            ProductDTO productDTO = jdbcTemplate.queryForObject(sql, new Object[]{productId}, (resultSet, rowNum) -> {
                ProductDTO product = new ProductDTO();
                product.setProduct_id(resultSet.getLong("product_id"));
                product.setDescription(resultSet.getString("description"));
                product.setProduct_name(resultSet.getString("product_name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setStock_quantity(resultSet.getInt("stock_quantity"));
                product.setUser_id(resultSet.getLong("user_id"));
                product.setShope_id(resultSet.getLong("shope_id"));
                product.setProduct_image(resultSet.getString("product_image"));
                product.setProduct_any_image(resultSet.getString("product_any_image"));
                product.setCategory_id(resultSet.getString("category_id"));
                product.setCreateBy(resultSet.getString("first_name"));
                return product;
            });

            ResponseWrapper<ProductDTO> responseWrapper = new ResponseWrapper<>("Product data retrieved successfully.", productDTO);
            return ResponseEntity.ok(responseWrapper);
        } catch (JwtException e) {
            // Token is invalid or has expired
            ResponseWrapper<ProductDTO> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
        } catch (Exception e) {
            String errorMessage = "product not found.";
            ResponseWrapper<ProductDTO> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @PostMapping("/product/insert")
    public ResponseEntity<ResponseWrapper<String>> insertProduct(@RequestBody ProductWithColorsRequest request,
                                                                 @RequestHeader("Authorization") String authorizationHeader) {
        try {
            product_entity product = request.getProduct();
            List<color_entity> colors = request.getColors();
            List<size_entity> sizes = request.getSizes();
            List<product_image_entity> productAnyImages = request.getProduct_images();


            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
            // Token is invalid or has expired
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Save the product using the repository
            productRepository.save(product);

            // Set the product_id in each color_entity and associate them with the product
            for (color_entity color : colors) {
                color.setProduct_id(product.getProduct_id());
            }
            for(size_entity size:sizes){
                size.setProduct_id(product.getProduct_id());
            }

            for(product_image_entity imageOne:productAnyImages){
                imageOne.setProduct_id(product.getProduct_id());
            }

            // Save the colors using the colorRepository
            colorRepository.saveAll(colors);
            sizeRepository.saveAll(sizes);
            productImageRepository.saveAll(productAnyImages);

            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Product and colors inserted successfully", null);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("An error occurred while inserting the product and colors", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
        }
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
            product_entity existingProduct = productRepository.findById(productId).orElse(null);

            if (existingProduct == null) {
                ResponseWrapper<product_entity> notFoundResponse = new ResponseWrapper<>("Product not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }

            // อัพเดตข้อมูลสินค้าที่ได้รับมาจาก Request ลงในสินค้าที่มีอยู่ในฐานข้อมูล
            existingProduct.setProduct_name(updatedProduct.getProduct_name());
            existingProduct.setDescription(updatedProduct.getDescription());
            // ... อัพเดตข้อมูลอื่น ๆ ตามต้องการ ...

            // บันทึกการเปลี่ยนแปลงลงในฐานข้อมูล
            productRepository.save(existingProduct);

            ResponseWrapper<product_entity> responseWrapper = new ResponseWrapper<>("Product updated successfully", existingProduct);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "product not found.";
            ResponseWrapper<product_entity> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @DeleteMapping("/shop/delete_product/{productId}")
    public ResponseEntity<ResponseWrapper<String>> deleteProduct(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Authorization header is missing or empty.", null);
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
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("You are not authorized to perform this action.", null);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseWrapper);
            }

            // Check if the product exists in the database
            if (!productRepository.existsById(productId)) {
                ResponseWrapper<String> notFoundResponse = new ResponseWrapper<>("Product not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }

            // Delete the product from the database
            productRepository.deleteById(productId);

            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Product deleted successfully", "Deleted");
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while retrieving product data.";
            ResponseWrapper<String> errorResponse = new ResponseWrapper<>(errorMessage, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
