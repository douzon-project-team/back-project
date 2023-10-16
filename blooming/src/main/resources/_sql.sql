DROP DATABASE IF EXISTS project;
CREATE DATABASE IF NOT EXISTS project;

USE project;

CREATE TABLE `employee`
(
    `employee_no` BIGINT      NOT NULL,
    `id`          VARCHAR(20) NOT NULL UNIQUE,
    `password`    VARCHAR(20) NOT NULL,
    `name`        VARCHAR(10) NOT NULL,
    `img`         BLOB        NULL,
    `role`        TINYINT(1)  NOT NULL DEFAULT 1,
    `tel`         VARCHAR(13) NULL,
    `email`       VARCHAR(30) NULL
);

CREATE TABLE `product`
(
    `product_no`   BIGINT      NOT NULL,
    `product_code` VARCHAR(5)  NOT NULL UNIQUE,
    `designation`  VARCHAR(10) NULL,
    `standard`     TEXT        NULL,
    `unit`         INT         NULL
);

CREATE TABLE `customer`
(
    `customer_no` BIGINT      NOT NULL,
    `code`        VARCHAR(5)  NOT NULL UNIQUE,
    `name`        VARCHAR(10) NOT NULL,
    `tel`         VARCHAR(13) NOT NULL
);

CREATE TABLE `instruction`
(
    `instruction_no`   VARCHAR(12) NOT NULL,
    `instruction_date` DATE        NOT NULL,
    `customer_no`      BIGINT      NOT NULL,
    `employee_no`      BIGINT      NOT NULL
);

CREATE TABLE `product_instruction`
(
    `product_no`     BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NULL
);

CREATE TABLE `delivery`
(
    `delivery_no` VARCHAR(12) NOT NULL,
    `status`      TINYINT(1)  NOT NULL,
    `date`        DATE        NOT NULL,
    `employee_no` BIGINT      NOT NULL,
    `customer_no` BIGINT      NOT NULL
);

CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NULL
);

CREATE TABLE `log`
(
    `log_no`      BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL,
    `log_info`    TEXT        NOT NULL,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL,
    `employee_no` BIGINT      NOT NULL
);

ALTER TABLE `employee`
    ADD CONSTRAINT `PK_EMPLOYEE` PRIMARY KEY (
                                              `employee_no`
        );

ALTER TABLE `product`
    ADD CONSTRAINT `PK_PRODUCT` PRIMARY KEY (
                                             `product_no`
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

ALTER TABLE `log`
    ADD CONSTRAINT `PK_LOG` PRIMARY KEY (
                                         `log_no`
        );

# ALTER TABLE `instruction`
#     ADD CONSTRAINT `FK_customer_TO_instruction_1` FOREIGN KEY (
#                                                                `customer_no`
#         )
#         REFERENCES `customer` (
#                                `customer_no`
#             );
#
# ALTER TABLE `instruction`
#     ADD CONSTRAINT `FK_employee_TO_instruction_1` FOREIGN KEY (
#                                                                `employee_no`
#         )
#         REFERENCES `employee` (
#                                `employee_no`
#             );
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
# ALTER TABLE `delivery`
#     ADD CONSTRAINT `FK_employee_TO_delivery_1` FOREIGN KEY (
#                                                             `employee_no`
#         )
#         REFERENCES `employee` (
#                                `employee_no`
#             );
#
# ALTER TABLE `delivery`
#     ADD CONSTRAINT `FK_customer_TO_delivery_1` FOREIGN KEY (
#                                                             `customer_no`
#         )
#         REFERENCES `customer` (
#                                `customer_no`
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
# ALTER TABLE `log`
#     ADD CONSTRAINT `FK_employee_TO_log_1` FOREIGN KEY (
#                                                        `employee_no`
#         )
#         REFERENCES `employee` (
#                                `employee_no`
#             );