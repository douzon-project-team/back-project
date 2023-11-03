DROP DATABASE IF EXISTS project;

CREATE DATABASE IF NOT EXISTS project;

USE project;

CREATE TABLE `employee`
(
    `employee_no` BIGINT      NOT NULL PRIMARY KEY,
    `id`          VARCHAR(20) NOT NULL UNIQUE,
    `password`    VARCHAR(20) NOT NULL,
    `name`        VARCHAR(30) NOT NULL,
    `img`         BLOB        NULL,
    `role`        TINYINT(1)  NOT NULL DEFAULT 1,
    `tel`         VARCHAR(13) NULL,
    `email`       VARCHAR(30) NULL
);

INSERT INTO `employee` (employee_no, id, password, name, img, role, tel, email)
VALUES (200001, 'admin', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200002, 'admin1', 'admin1', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200003, 'admin2', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200004, 'admin3', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200005, 'admin4', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200006, 'admin5', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200007, 'admin6', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200008, 'admin7', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200009, 'admin8', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200010, 'admin9', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200011, 'admin10', 'admin', 'admin', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '01011112222', 'admin@admin.com'),
       (200012, 'member', 'member', 'member', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '01022223333', 'member@member.com');

CREATE TABLE `product`
(
    `product_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `product_code` VARCHAR(6)  NOT NULL UNIQUE,
    `designation`  VARCHAR(45) NOT NULL,
    `standard`     TEXT        NOT NULL,
    `unit`         INT         NOT NULL
);

INSERT INTO product (product_code, designation, standard, unit)
VALUES ('VV0001', 'pencil', '2.5cm * 10cm', 10);

CREATE TABLE `customer`
(
    `customer_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_code` VARCHAR(5)  NOT NULL UNIQUE,
    `customer_name` VARCHAR(10) NOT NULL,
    `customer_tel`  VARCHAR(13) NOT NULL
);

CREATE TABLE `instruction`
(
    `instruction_no`   VARCHAR(12) PRIMARY KEY,
    `instruction_date` DATE       NOT NULL DEFAULT NOW(),
    `customer_no`      BIGINT     NOT NULL,
    `employee_no`      BIGINT     NOT NULL,
    `progress_status`  TINYINT(1) NOT NULL DEFAULT 0,
    `expiration_date`  DATE       NOT NULL
);

CREATE TABLE `product_instruction`
(
    `product_no`     BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NOT NULL
);

CREATE TABLE `delivery`
(
    `delivery_no`     VARCHAR(12) PRIMARY KEY,
    `progress_status` TINYINT(1) NOT NULL DEFAULT 0,
    `delivery_date`   DATE       NOT NULL,
    `employee_no`     BIGINT     NOT NULL,
    `customer_no`     BIGINT     NOT NULL
);

CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `product_no`     BIGINT      NOT NULL,
    `amount`         INT         NOT NULL
);

CREATE TABLE `employee_log`
(
    `log_no`      BIGINT PRIMARY KEY,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `target_no`   BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

CREATE TABLE `product_log`
(
    `log_no`      BIGINT PRIMARY KEY,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `product_no`  BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

CREATE TABLE `instruction_log`
(
    `log_no`         BIGINT PRIMARY KEY,
    `ip_address`     VARCHAR(20) NOT NULL,
    `date`           DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`    BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `type`           TINYINT(1)  NOT NULL
);

CREATE TABLE `delivery_log`
(
    `log_no`      BIGINT PRIMARY KEY,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `delivery_no` VARCHAR(12) NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

# ALTER TABLE `product_instruction`
#     ADD CONSTRAINT `PK_PRODUCT_INSTRUCTION` PRIMARY KEY (
#                                                          `product_no`,
#                                                          `instruction_no`
#         );

# ALTER TABLE `delivery_instruction`
#     ADD CONSTRAINT `PK_DELIVERY_INSTRUCTION` PRIMARY KEY (
#                                                           `delivery_no`,
#                                                           `instruction_no`
#         );

# ALTER TABLE `product_instruction`
#     ADD CONSTRAINT `FK_product_TO_product_instruction_1` FOREIGN KEY (
#                                                                       `product_no`
#         )
#         REFERENCES `product` (
#                               `product_no`
#             );
#
# ALTER TABLE `product_instruction`
#     ADD CONSTRAINT `FK_instruction_TO_product_instruction_1` FOREIGN KEY (
#                                                                           `instruction_no`
#         )
#         REFERENCES `instruction` (
#                                   `instruction_no`
#             );
#
# ALTER TABLE `delivery_instruction`
#     ADD CONSTRAINT `FK_delivery_TO_delivery_instruction_1` FOREIGN KEY (
#                                                                         `delivery_no`
#         )
#         REFERENCES `delivery` (
#                                `delivery_no`
#             );
#
# ALTER TABLE `delivery_instruction`
#     ADD CONSTRAINT `FK_instruction_TO_delivery_instruction_1` FOREIGN KEY (
#                                                                            `instruction_no`
#         )
#         REFERENCES `instruction` (
#                                   `instruction_no`
#             );
#

# Instruction 자동 채번 Trigger
DELIMITER //
CREATE TRIGGER instruction_no_trigger
    BEFORE INSERT
    ON instruction
    FOR EACH ROW
BEGIN
    SET NEW.instruction_no = concat('WO', substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                    lpad((SELECT IFNULL(MAX(SUBSTRING(instruction_no, 7, 6)) + 1, 1)
                                          FROM instruction), 6, '0'));
END;
//
DELIMITER ;

# Delivery 자동 채번 Trigger
# DELIMITER //
# CREATE TRIGGER delivery_no_trigger
#     BEFORE INSERT ON delivery
#     FOR EACH ROW
# BEGIN
#     SET NEW.delivery_no = concat('MW',substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
#                                  lpad((SELECT IFNULL(MAX(SUBSTRING(delivery_no, 7, 6))+1, 1) FROM delivery), 6, '0'));
# END;
# //
# DELIMITER ;