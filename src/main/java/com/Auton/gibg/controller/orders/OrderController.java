package com.Auton.gibg.controller.orders;

import com.Auton.gibg.entity.order.OrderDetailEntity;
import com.Auton.gibg.entity.order.OrderEntity;
import com.Auton.gibg.middleware.authToken;
import com.Auton.gibg.repository.order.OrderDetailRepository;
import com.Auton.gibg.repository.order.OrderRepository;
import com.Auton.gibg.response.ResponseWrapper;
import com.Auton.gibg.response.order.orderResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.cloudinary.AccessControlRule.AccessType.token;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final JdbcTemplate jdbcTemplate;
    private final authToken authService;
    @Value("${jwt_secret}")
    private String jwt_secret;
    @Autowired
    private OrderRepository orderRepository; // Assuming you have an OrderRepository
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderController(JdbcTemplate jdbcTemplate, authToken authService) {
        this.jdbcTemplate = jdbcTemplate;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<String>> createOrder(
            @RequestBody orderResponse orderResponse,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Get the authenticated user's ID from the claims
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret) // Replace with your secret key
                    .parseClaimsJws(token)
                    .getBody();

            Long authenticatedUserId = claims.get("user_id", Long.class);

            Long statusIdFromRequest = orderResponse.getStatus_id(); // Access status_id directly

            // Create an instance of OrderEntity
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUser_id(authenticatedUserId);
            orderEntity.setStatus_id(statusIdFromRequest);
            orderEntity.setCreated_at(new Date()); // Set current date

            // Save the orderEntity into the database using orderRepository
            orderRepository.save(orderEntity);

            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("Order created successfully.", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("An error occurred while creating the order.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
        }
    }
    @GetMapping("/order/all")
    public ResponseEntity<ResponseWrapper<List<OrderEntity>>> getAllOrders(
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Validate authorization using authService
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                // Token is invalid or has expired
                ResponseWrapper<List<OrderEntity>> responseWrapper = new ResponseWrapper<>("Token is invalid.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseWrapper);
            }

            // Get orders from the database using orderRepository
            List<OrderEntity> orders = orderRepository.findAll();

            ResponseWrapper<List<OrderEntity>> responseWrapper = new ResponseWrapper<>("Orders retrieved successfully.", orders);
            return ResponseEntity.ok(responseWrapper);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            ResponseWrapper<List<OrderEntity>> responseWrapper = new ResponseWrapper<>("An error occurred while retrieving orders.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
        }
    }

    @PostMapping("/add-detail/{orderId}")
    public ResponseEntity<ResponseWrapper<String>> addOrderDetail(
            @PathVariable Long orderId,
            @RequestBody OrderDetailEntity orderDetail,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            ResponseEntity<?> authResponse = authService.validateAuthorizationHeader(authorizationHeader);
            if (authResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseWrapper<>("Authorization failed.", null));
            }

            // Get the authenticated user's ID from the claims
            String token = authorizationHeader.substring("Bearer ".length());

            Claims claims = Jwts.parser()
                    .setSigningKey(jwt_secret)
                    .parseClaimsJws(token)
                    .getBody();

            Long authenticatedUserId = claims.get("user_id", Long.class);

            Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                OrderEntity order = optionalOrder.get();
                orderDetail.setOrder(order);
                orderDetail.setCreateAt(new Date()); // Set current date

                orderDetailRepository.save(orderDetail);

                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("Order detail added successfully.", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("Order not found.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>("Error adding order detail.", null));
        }
    }



}
