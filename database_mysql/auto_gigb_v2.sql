-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 20, 2023 at 07:44 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `auto_gigb_v2`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking_status`
--

CREATE TABLE `booking_status` (
  `status_id` int(11) NOT NULL,
  `status_name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `login_log`
--

CREATE TABLE `login_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `login_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `ip_address` varchar(45) NOT NULL,
  `device_info` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `point`
--

CREATE TABLE `point` (
  `point_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `points` int(11) NOT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `earned_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_color`
--

CREATE TABLE `product_color` (
  `color_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `color` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_hashtag`
--

CREATE TABLE `product_hashtag` (
  `size_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `tag` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_size`
--

CREATE TABLE `product_size` (
  `size_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `size` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_address`
--

CREATE TABLE `tb_address` (
  `address_id` int(11) NOT NULL,
  `street_address` varchar(100) NOT NULL,
  `state` varchar(50) NOT NULL,
  `postal_code` varchar(50) NOT NULL,
  `country` varchar(50) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tb_address`
--

INSERT INTO `tb_address` (`address_id`, `street_address`, `state`, `postal_code`, `country`, `latitude`, `longitude`, `create_at`) VALUES
(32, '123 Main St', 'California', '12345', 'USA', 37.00000000, 122.00000000, '2023-08-19 08:40:54'),
(33, '123 Main St', 'California', '12345', 'USA', 37.00000000, 122.00000000, '2023-08-20 03:38:20');

-- --------------------------------------------------------

--
-- Table structure for table `tb_booking`
--

CREATE TABLE `tb_booking` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `booking_date` date NOT NULL,
  `booking_time` time NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_categories`
--

CREATE TABLE `tb_categories` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `parent_category_id` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_comments`
--

CREATE TABLE `tb_comments` (
  `comment_id` int(11) NOT NULL,
  `rating` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `parent_comment_id` int(11) DEFAULT NULL,
  `comment_text` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_coupons`
--

CREATE TABLE `tb_coupons` (
  `coupon_id` int(11) NOT NULL,
  `coupon_code` varchar(20) NOT NULL,
  `discount` decimal(5,2) NOT NULL,
  `valid_from` date DEFAULT NULL,
  `valid_until` date DEFAULT NULL,
  `max_usage` int(11) DEFAULT NULL,
  `remaining_usage` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_products`
--

CREATE TABLE `tb_products` (
  `product_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL,
  `shope_id` int(11) NOT NULL,
  `product_image` varchar(150) NOT NULL,
  `product_any_image` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_role`
--

CREATE TABLE `tb_role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tb_role`
--

INSERT INTO `tb_role` (`role_id`, `role_name`, `description`, `created_at`) VALUES
(1, 'Super Admin', 'Main of admin office', '2023-08-15 04:11:51'),
(2, 'admin', NULL, '2023-08-15 05:24:43'),
(3, 'shop owner', NULL, '2023-08-15 05:24:43'),
(4, 'General user', NULL, '2023-08-15 05:25:07'),
(5, 'Deviloper', NULL, '2023-08-15 05:25:29');

-- --------------------------------------------------------

--
-- Table structure for table `tb_service`
--

CREATE TABLE `tb_service` (
  `service_id` int(11) NOT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `service_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration_minutes` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_shop`
--

CREATE TABLE `tb_shop` (
  `shop_id` int(11) NOT NULL,
  `shop_name` varchar(100) NOT NULL,
  `street_address` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `latitude` decimal(10,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `shop_image` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `monday_open` time DEFAULT NULL,
  `monday_close` time DEFAULT NULL,
  `tuesday_open` time DEFAULT NULL,
  `tuesday_close` time DEFAULT NULL,
  `wednesday_open` time DEFAULT NULL,
  `wednesday_close` time DEFAULT NULL,
  `thursday_open` time DEFAULT NULL,
  `thursday_close` time DEFAULT NULL,
  `friday_open` time DEFAULT NULL,
  `friday_close` time DEFAULT NULL,
  `saturday_open` time DEFAULT NULL,
  `saturday_close` time DEFAULT NULL,
  `sunday_open` time DEFAULT NULL,
  `sunday_close` time DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tb_shop`
--

INSERT INTO `tb_shop` (`shop_id`, `shop_name`, `street_address`, `city`, `state`, `postal_code`, `country`, `latitude`, `longitude`, `type_id`, `shop_image`, `monday_open`, `monday_close`, `tuesday_open`, `tuesday_close`, `wednesday_open`, `wednesday_close`, `thursday_open`, `thursday_close`, `friday_open`, `friday_close`, `saturday_open`, `saturday_close`, `sunday_open`, `sunday_close`, `created_at`) VALUES
(3, 'Example Shop', '123 Main St', 'Cityville', 'State', '12345', 'Country', 37.12345678, -122.98765432, 1, 'C:\\Auto Office\\Project\\GIGI_admin\\src\\main\\resources\\static\\uploaded\\images\\7985802c-bea3-46ea-a32e-d39ef5d5e616.png', '08:00:00', '18:00:00', '08:00:00', '18:00:00', '08:00:00', '18:00:00', '08:00:00', '18:00:00', '08:00:00', '18:00:00', '10:00:00', '16:00:00', '10:00:00', '16:00:00', '2023-08-20 03:38:20');

-- --------------------------------------------------------

--
-- Table structure for table `tb_shop_types`
--

CREATE TABLE `tb_shop_types` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_shop_types`
--

INSERT INTO `tb_shop_types` (`type_id`, `type_name`, `created_at`) VALUES
(2, 'car care', '2023-08-17 09:40:00'),
(3, 'car care2', '2023-08-17 09:50:31'),
(4, 'car care2', '2023-08-17 10:07:15');

-- --------------------------------------------------------

--
-- Table structure for table `tb_users`
--

CREATE TABLE `tb_users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` enum('Male','Female','Other') DEFAULT NULL,
  `profile_picture` varchar(150) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_login` timestamp NULL DEFAULT NULL,
  `is_active` enum('Active','Pending','Block','') DEFAULT NULL,
  `bio` varchar(150) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `vehicle_id` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tb_users`
--

INSERT INTO `tb_users` (`user_id`, `email`, `password`, `first_name`, `last_name`, `birthdate`, `gender`, `profile_picture`, `created_at`, `last_login`, `is_active`, `bio`, `role_id`, `vehicle_id`, `address_id`, `shop_id`) VALUES
(19, 'example@example.com', '$2a$10$2aAhJNcFjqFLAnRV.EQHoOkrFTpjaCm2ykL/0YlvVXDsEBgSkQQWO', 'John', 'Doe', '2000-01-01', 'Male', 'C:\\Auto Office\\Project\\GIGI_admin\\src\\main\\resources\\static\\uploaded\\images\\7985802c-bea3-46ea-a32e-d39ef5d5e616.png', '2023-08-19 08:40:55', NULL, 'Active', 'A brief biography.', 1, NULL, 32, NULL),
(20, 'example11@example.com', '$2a$10$48iulaf.Z29t0KmDEW.ff.fZ1QYbbzGgTnOo7SVJfSJIWHG6B4/Vu', 'John', 'Doe', '2000-01-01', 'Male', 'C:\\Auto Office\\Project\\GIGI_admin\\src\\main\\resources\\static\\uploaded\\images\\7985802c-bea3-46ea-a32e-d39ef5d5e616.png', '2023-08-20 03:38:20', NULL, 'Active', 'A brief biography.', 3, NULL, 33, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tb_vehicles`
--

CREATE TABLE `tb_vehicles` (
  `vehicle_id` int(11) DEFAULT NULL,
  `model` varchar(50) NOT NULL,
  `year` int(11) NOT NULL,
  `color` varchar(50) NOT NULL,
  `vehicle_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_shop_view`
-- (See below for the actual view)
--
CREATE TABLE `user_shop_view` (
`user_id` int(11)
,`email` varchar(50)
,`first_name` varchar(50)
,`last_name` varchar(50)
,`birthdate` date
,`gender` enum('Male','Female','Other')
,`profile_picture` varchar(150)
,`created_at` timestamp
,`last_login` timestamp
,`is_active` enum('Active','Pending','Block','')
,`bio` varchar(150)
,`role_id` int(11)
,`role_name` varchar(50)
,`user_address` varchar(100)
,`user_state` varchar(50)
,`user_postal_code` varchar(50)
,`user_country` varchar(50)
,`user_latitude` decimal(10,8)
,`user_longitude` decimal(11,8)
,`shop_id` int(11)
,`shop_name` varchar(100)
,`shop_address` varchar(100)
,`city` varchar(50)
,`shop_state` varchar(50)
,`shop_postal_code` varchar(20)
,`shop_country` varchar(50)
,`shop_latitude` decimal(10,8)
,`shop_longitude` decimal(11,8)
,`type_id` int(11)
,`shop_image` varchar(150)
,`monday_open` time
,`monday_close` time
,`tuesday_open` time
,`tuesday_close` time
,`wednesday_open` time
,`wednesday_close` time
,`thursday_open` time
,`thursday_close` time
,`friday_open` time
,`friday_close` time
,`saturday_open` time
,`saturday_close` time
,`sunday_open` time
,`sunday_close` time
,`shop_created_at` timestamp
);

-- --------------------------------------------------------

--
-- Structure for view `user_shop_view`
--
DROP TABLE IF EXISTS `user_shop_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_shop_view`  AS SELECT `tb_users`.`user_id` AS `user_id`, `tb_users`.`email` AS `email`, `tb_users`.`first_name` AS `first_name`, `tb_users`.`last_name` AS `last_name`, `tb_users`.`birthdate` AS `birthdate`, `tb_users`.`gender` AS `gender`, `tb_users`.`profile_picture` AS `profile_picture`, `tb_users`.`created_at` AS `created_at`, `tb_users`.`last_login` AS `last_login`, `tb_users`.`is_active` AS `is_active`, `tb_users`.`bio` AS `bio`, `tb_users`.`role_id` AS `role_id`, `tb_role`.`role_name` AS `role_name`, `tb_address`.`street_address` AS `user_address`, `tb_address`.`state` AS `user_state`, `tb_address`.`postal_code` AS `user_postal_code`, `tb_address`.`country` AS `user_country`, `tb_address`.`latitude` AS `user_latitude`, `tb_address`.`longitude` AS `user_longitude`, `tb_shop`.`shop_id` AS `shop_id`, `tb_shop`.`shop_name` AS `shop_name`, `tb_shop`.`street_address` AS `shop_address`, `tb_shop`.`city` AS `city`, `tb_shop`.`state` AS `shop_state`, `tb_shop`.`postal_code` AS `shop_postal_code`, `tb_shop`.`country` AS `shop_country`, `tb_shop`.`latitude` AS `shop_latitude`, `tb_shop`.`longitude` AS `shop_longitude`, `tb_shop`.`type_id` AS `type_id`, `tb_shop`.`shop_image` AS `shop_image`, `tb_shop`.`monday_open` AS `monday_open`, `tb_shop`.`monday_close` AS `monday_close`, `tb_shop`.`tuesday_open` AS `tuesday_open`, `tb_shop`.`tuesday_close` AS `tuesday_close`, `tb_shop`.`wednesday_open` AS `wednesday_open`, `tb_shop`.`wednesday_close` AS `wednesday_close`, `tb_shop`.`thursday_open` AS `thursday_open`, `tb_shop`.`thursday_close` AS `thursday_close`, `tb_shop`.`friday_open` AS `friday_open`, `tb_shop`.`friday_close` AS `friday_close`, `tb_shop`.`saturday_open` AS `saturday_open`, `tb_shop`.`saturday_close` AS `saturday_close`, `tb_shop`.`sunday_open` AS `sunday_open`, `tb_shop`.`sunday_close` AS `sunday_close`, `tb_shop`.`created_at` AS `shop_created_at` FROM (((`tb_users` left join `tb_role` on(`tb_users`.`role_id` = `tb_role`.`role_id`)) left join `tb_shop` on(`tb_users`.`shop_id` = `tb_shop`.`shop_id`)) left join `tb_address` on(`tb_users`.`address_id` = `tb_address`.`address_id`)) WHERE `tb_users`.`role_id` = 3 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking_status`
--
ALTER TABLE `booking_status`
  ADD PRIMARY KEY (`status_id`);

--
-- Indexes for table `login_log`
--
ALTER TABLE `login_log`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `point`
--
ALTER TABLE `point`
  ADD PRIMARY KEY (`point_id`);

--
-- Indexes for table `product_color`
--
ALTER TABLE `product_color`
  ADD PRIMARY KEY (`color_id`);

--
-- Indexes for table `product_hashtag`
--
ALTER TABLE `product_hashtag`
  ADD PRIMARY KEY (`size_id`);

--
-- Indexes for table `product_size`
--
ALTER TABLE `product_size`
  ADD PRIMARY KEY (`size_id`);

--
-- Indexes for table `tb_address`
--
ALTER TABLE `tb_address`
  ADD PRIMARY KEY (`address_id`);

--
-- Indexes for table `tb_booking`
--
ALTER TABLE `tb_booking`
  ADD PRIMARY KEY (`booking_id`);

--
-- Indexes for table `tb_categories`
--
ALTER TABLE `tb_categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `tb_comments`
--
ALTER TABLE `tb_comments`
  ADD PRIMARY KEY (`comment_id`);

--
-- Indexes for table `tb_coupons`
--
ALTER TABLE `tb_coupons`
  ADD PRIMARY KEY (`coupon_id`);

--
-- Indexes for table `tb_products`
--
ALTER TABLE `tb_products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `tb_role`
--
ALTER TABLE `tb_role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `tb_service`
--
ALTER TABLE `tb_service`
  ADD PRIMARY KEY (`service_id`);

--
-- Indexes for table `tb_shop`
--
ALTER TABLE `tb_shop`
  ADD PRIMARY KEY (`shop_id`);

--
-- Indexes for table `tb_shop_types`
--
ALTER TABLE `tb_shop_types`
  ADD PRIMARY KEY (`type_id`);

--
-- Indexes for table `tb_users`
--
ALTER TABLE `tb_users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking_status`
--
ALTER TABLE `booking_status`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login_log`
--
ALTER TABLE `login_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `point`
--
ALTER TABLE `point`
  MODIFY `point_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product_color`
--
ALTER TABLE `product_color`
  MODIFY `color_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product_hashtag`
--
ALTER TABLE `product_hashtag`
  MODIFY `size_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product_size`
--
ALTER TABLE `product_size`
  MODIFY `size_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_address`
--
ALTER TABLE `tb_address`
  MODIFY `address_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `tb_booking`
--
ALTER TABLE `tb_booking`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_categories`
--
ALTER TABLE `tb_categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_comments`
--
ALTER TABLE `tb_comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_coupons`
--
ALTER TABLE `tb_coupons`
  MODIFY `coupon_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_products`
--
ALTER TABLE `tb_products`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_role`
--
ALTER TABLE `tb_role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tb_service`
--
ALTER TABLE `tb_service`
  MODIFY `service_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tb_shop`
--
ALTER TABLE `tb_shop`
  MODIFY `shop_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_shop_types`
--
ALTER TABLE `tb_shop_types`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tb_users`
--
ALTER TABLE `tb_users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
