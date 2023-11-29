DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`
(
    `employee_no` BIGINT      NOT NULL PRIMARY KEY,
    `id`          VARCHAR(20) NOT NULL UNIQUE,
    `password`    VARCHAR(20) NOT NULL,
    `name`        VARCHAR(30) NOT NULL,
    `img`         TEXT        NULL,
    `role`        TINYINT(1)  NOT NULL DEFAULT 1,
    `tel`         VARCHAR(13) NULL,
    `email`       VARCHAR(30) NULL
);

DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`
(
    `todo_no`     BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `employee_no` BIGINT NOT NULL,
    `content`     TEXT   NOT NULL,
    `created`     DATE       DEFAULT NOW(),
    `checked`     TINYINT(1) DEFAULT 0
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `product_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `product_code` VARCHAR(6)  NOT NULL UNIQUE,
    `product_name` VARCHAR(45) NOT NULL,
    `standard`     TEXT        NOT NULL,
    `unit`         INT         NOT NULL
);

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`
(
    `customer_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_code` VARCHAR(5)  NOT NULL UNIQUE,
    `customer_name` VARCHAR(10) NOT NULL,
    `customer_tel`  VARCHAR(13) NOT NULL,
    `ceo`           VARCHAR(10) NOT NULL,
    `sector`        VARCHAR(20) NOT NULL
);

DROP TABLE IF EXISTS `instruction`;
CREATE TABLE `instruction`
(
    `instruction_no`   VARCHAR(12) PRIMARY KEY,
    `instruction_date` DATE       NOT NULL DEFAULT NOW(),
    `customer_no`      BIGINT     NOT NULL,
    `employee_no`      BIGINT     NOT NULL,
    `progress_status`  TINYINT(1) NOT NULL DEFAULT 0,
    `expiration_date`  DATE       NOT NULL
);

DROP TABLE IF EXISTS `product_instruction`;
CREATE TABLE `product_instruction`
(
    `product_no`     BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NOT NULL,
    `remain_amount`  INT         NOT NULL
);

DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery`
(
    `delivery_no`     VARCHAR(12) PRIMARY KEY,
    `progress_status` TINYINT(1) NOT NULL DEFAULT 0,
    `delivery_date`   DATE       NOT NULL,
    `employee_no`     BIGINT     NOT NULL
);

DROP TABLE IF EXISTS `delivery_instruction`;
CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `product_no`     BIGINT      NOT NULL,
    `amount`         INT         NOT NULL
);

DROP TABLE IF EXISTS `employee_log`;
CREATE TABLE `employee_log`
(
    `log_no`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `target_no`   BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

DROP TABLE IF EXISTS `product_log`;
CREATE TABLE `product_log`
(
    `log_no`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `product_no`  BIGINT      NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);

DROP TABLE IF EXISTS `instruction_log`;
CREATE TABLE `instruction_log`
(
    `log_no`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`     VARCHAR(20) NOT NULL,
    `date`           DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`    BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `type`           TINYINT(1)  NOT NULL
);

DROP TABLE IF EXISTS `delivery_log`;
CREATE TABLE `delivery_log`
(
    `log_no`      BIGINT PRIMARY KEY,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `delivery_no` VARCHAR(12) NOT NULL,
    `type`        TINYINT(1)  NOT NULL
);