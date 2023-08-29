package com.Auton.gibg.controller.shop;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/shop_status")
public class ShopStatusController {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ShopStatusController(JdbcTemplate jdbcTemplate) {
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
}
