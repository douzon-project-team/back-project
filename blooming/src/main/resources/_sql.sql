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

CREATE TABLE `product`
(
    `product_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `product_code` VARCHAR(6)         NOT NULL UNIQUE,
    `designation`  VARCHAR(45)        NULL,
    `standard`     TEXT               NULL,
    `unit`         INT                NULL
);

INSERT INTO product (product_code,designation,standard,unit) VALUES ('VV0001','pencil','2.5cm * 10cm',10);

CREATE TABLE `customer`
(
    `customer_no` BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `code`        VARCHAR(5)  NOT NULL UNIQUE,
    `name`        VARCHAR(10) NOT NULL,
    `tel`         VARCHAR(13) NOT NULL
);

CREATE TABLE `instruction`
(
    `instruction_no`   VARCHAR(12) NOT NULL,
    `instruction_date` DATE        NOT NULL DEFAULT NOW(),
    `customer_no`      BIGINT      NOT NULL,
    `employee_no`      BIGINT      NOT NULL,
    `progress_status`  TINYINT(1)  NOT NULL DEFAULT 0,
    `expiration_date`  DATE        NOT NULL
);

CREATE TABLE `product_instruction`
(
    `product_no`     BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NULL
);

CREATE TABLE `delivery`
(
    `delivery_no`     VARCHAR(12) NOT NULL,
    `progress_status` TINYINT(1)  NOT NULL DEFAULT 0,
    `date`            DATE        NOT NULL DEFAULT NOW(),
    `employee_no`     BIGINT      NOT NULL,
    `customer_no`     BIGINT      NOT NULL
);

CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NOT NULL
);

CREATE TABLE `employee_log`
(
    `log_no`      BIGINT      NOT NULL,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `target_no`   BIGINT      NOT NULL,
    `tpoe`        TINYINT(1)  NOT NULL
);

CREATE TABLE `product_log`
(
    `log_no`      BIGINT      NOT NULL,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `product_no`  BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

CREATE TABLE `instruction_log`
(
    `log_no`         BIGINT      NOT NULL,
    `ip_address`     VARCHAR(20) NOT NULL,
    `date`           DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`    BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `type`           TINYINT(1)  NOT NULL
);

CREATE TABLE `delivery_log`
(
    `log_no`      BIGINT      NOT NULL,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `delivery_no` VARCHAR(12) NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

ALTER TABLE `customer`
    ADD CONSTRAINT `PK_CUSTOMER` PRIMARY KEY (
                                              `customer_no`
        );

ALTER TABLE `instruction`
    ADD CONSTRAINT `PK_INSTRUCTION` PRIMARY KEY (
                                                 `instruction_no`
        );

ALTER TABLE `product_instruction`
    ADD CONSTRAINT `PK_PRODUCT_INSTRUCTION` PRIMARY KEY (
                                                         `product_no`,
                                                         `instruction_no`
        );

ALTER TABLE `delivery`
    ADD CONSTRAINT `PK_DELIVERY` PRIMARY KEY (
                                              `delivery_no`
        );

ALTER TABLE `delivery_instruction`
    ADD CONSTRAINT `PK_DELIVERY_INSTRUCTION` PRIMARY KEY (
                                                          `delivery_no`,
                                                          `instruction_no`
        );

ALTER TABLE `employee_log`
    ADD CONSTRAINT `PK_EMPLOYEE_LOG` PRIMARY KEY (
                                                  `log_no`
        );

ALTER TABLE `product_log`
    ADD CONSTRAINT `PK_PRODUCT_LOG` PRIMARY KEY (
                                                 `log_no`
        );

ALTER TABLE `instruction_log`
    ADD CONSTRAINT `PK_INSTRUCTION_LOG` PRIMARY KEY (
                                                     `log_no`
        );

ALTER TABLE `delivery_log`
    ADD CONSTRAINT `PK_DELIVERY_LOG` PRIMARY KEY (
                                                  `log_no`
        );
#
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
