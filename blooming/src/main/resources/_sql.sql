DROP DATABASE IF EXISTS project;

CREATE DATABASE IF NOT EXISTS project;

USE project;
drop table employee;
CREATE TABLE `employee`
(
    `employee_no`   BIGINT      NOT NULL PRIMARY KEY,
    `id`            VARCHAR(20) NOT NULL UNIQUE,
    `password`      VARCHAR(20) NOT NULL,
    `name`          VARCHAR(30) NOT NULL,
    `img`           TEXT        NULL,
    `role`          TINYINT(1)  NOT NULL DEFAULT 1,
    `tel`           VARCHAR(13) NULL,
    `email`         VARCHAR(30) NULL
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
    `product_no`    BIGINT             PRIMARY KEY AUTO_INCREMENT,
    `product_code`  VARCHAR(6)         NOT NULL UNIQUE,
    `product_name`  VARCHAR(10)        NOT NULL,
    `standard`      TEXT               NOT NULL,
    `unit`          INT                NOT NULL
);

INSERT INTO product (product_code, product_name, standard, unit)
VALUES ('AP0001', 'MacBook Air 13', '1000mm * 100mm', 10),
       ('AP0002', 'MacBook Air 15', '1000mm * 100mm', 5),
       ('AP0003', 'iPhone 15 Pro', '250mm * 110mm', 20),
       ('AP0004', 'Apple Watch Series 9', '100mm * 60mm', 100),
       ('AP0005', 'AirPods Pro MagSafe', '100mm * 100mm', 21),
       ('AP0006', 'AirTag', '50mm * 50mm', 63),
       ('AP0007', 'iPad Pro', '400mm * 250mm', 14),
       ('AP0008', 'iPad Air', '400mm * 250mm', 40),
       ('AP0009', 'iPad 10', '400mm * 250mm', 30),
       ('AP0010', 'AirPods Pro 2', '100mm * 100mm', 12),
       ('AP0011', 'AirPods 3', '100mm * 100mm', 20),
       ('AP0012', 'AirPods 2', '100mm * 100mm', 100);

CREATE TABLE `customer`
(
    `customer_no`      BIGINT      PRIMARY KEY AUTO_INCREMENT,
    `customer_code`    VARCHAR(5)  NOT NULL UNIQUE,
    `customer_name`    VARCHAR(10) NOT NULL,
    `customer_tel`     VARCHAR(13) NOT NULL
);

INSERT INTO customer VALUES (1, 'C0001', 'samsung', '010-1234-1234'),
                            (2, 'C0002', 'Lg', '010-3214-3214'),
                            (3, 'C0003', 'Douzone', '010-4444-4444');

CREATE TABLE `instruction`
(
    `instruction_no`   VARCHAR(12) PRIMARY KEY ,
    `instruction_date` DATE        NOT NULL DEFAULT NOW(),
    `customer_no`      BIGINT      NOT NULL,
    `employee_no`      BIGINT      NOT NULL,
    `progress_status`  TINYINT(1)  NOT NULL DEFAULT 0,
    `expiration_date`  DATE        NOT NULL
);

INSERT INTO instruction(instruction_date, customer_no, employee_no, expiration_date) VALUES ('2023-10-22', 3, 200003, '2023-11-21'),
                                                                                            ('2023-10-25', 2, 200005, '2023-11-25');

CREATE TABLE `product_instruction`
(
    `product_no`     BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `amount`         INT         NOT NULL,
    `remain_amount`  INT         NOT NULL
);

INSERT INTO product_instruction VALUES (1, 'WO2311000001', 15, 5),
                                       (2, 'WO2311000001', 20, 5),
                                       (1, 'WO2311000002', 15, 10),
                                       (1, 'WO2311000002', 30, 20);

CREATE TABLE `delivery`
(
    `delivery_no`     VARCHAR(12) PRIMARY KEY ,
    `progress_status` TINYINT(1)  NOT NULL DEFAULT 0,
    `delivery_date`   DATE        NOT NULL,
    `employee_no`     BIGINT      NOT NULL
);

INSERT INTO delivery(delivery_date, employee_no) VALUES ('2023-11-29', 200004);

CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `product_no`     BIGINT      NOT NULL,
    `amount`         INT         NOT NULL
);

INSERT INTO delivery_instruction VALUES ('MW2311000001', 'WO2311000001', 1, 10),
                                        ('MW2311000001', 'WO2311000001', 2, 15),
                                        ('MW2311000001', 'WO2311000002', 1, 5),
                                        ('MW2311000001', 'WO2311000002', 2, 10);

CREATE TABLE `employee_log`
(
    `log_no`        BIGINT      PRIMARY KEY ,
    `ip_address`    VARCHAR(20) NOT NULL,
    `date`          DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`   BIGINT      NOT NULL,
    `target_no`     BIGINT      NOT NULL,
    `type`          TINYINT(1)  NOT NULL
);

CREATE TABLE `product_log`
(
    `log_no`         BIGINT      PRIMARY KEY ,
    `ip_address`    VARCHAR(20) NOT NULL,
    `date`          DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`   BIGINT      NOT NULL,
    `product_no`    BIGINT      NOT NULL,
    `type`          TINYINT(1)  NOT NULL
);

CREATE TABLE `instruction_log`
(
    `log_no`         BIGINT      PRIMARY KEY ,
    `ip_address`     VARCHAR(20) NOT NULL,
    `date`           DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`    BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `type`           TINYINT(1)  NOT NULL
);

CREATE TABLE `delivery_log`
(
    `log_no`        BIGINT      PRIMARY KEY ,
    `ip_address`    VARCHAR(20) NOT NULL,
    `date`          DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`   BIGINT      NOT NULL,
    `delivery_no`   VARCHAR(12) NOT NULL,
    `type`          TINYINT(1)  NOT NULL
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
#             )
#     ON DELETE CASCADE;
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
#     ON DELETE CASCADE;
#

# Instruction 자동 채번 Trigger
DELIMITER //
CREATE TRIGGER instruction_no_trigger
    BEFORE INSERT ON instruction
    FOR EACH ROW
BEGIN
    SET NEW.instruction_no = concat('WO',substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                    lpad((SELECT IFNULL(MAX(SUBSTRING(instruction_no, 7, 6))+1, 1) FROM instruction), 6, '0'));
END;
//
DELIMITER ;

# Delivery 자동 채번 Trigger
DELIMITER //
CREATE TRIGGER delivery_no_trigger
    BEFORE INSERT ON delivery
    FOR EACH ROW
BEGIN
    SET NEW.delivery_no = concat('MW',substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                 lpad((SELECT IFNULL(MAX(SUBSTRING(delivery_no, 7, 6))+1, 1) FROM delivery), 6, '0'));
END;
//
DELIMITER ;

# Delivery 등록시 instruction의 잔량, 진행 상태 변화 Trigger
drop trigger insert_remain_amount;
DELIMITER //
CREATE TRIGGER insert_remain_amount
    AFTER INSERT ON delivery_instruction
    FOR EACH ROW
BEGIN
    DECLARE new_remain_amount INT;
    SET new_remain_amount = (SELECT remain_amount - NEW.amount FROM product_instruction
                             WHERE instruction_no = NEW.instruction_no AND product_no = NEW.product_no);

    UPDATE product_instruction
    SET remain_amount = new_remain_amount
    WHERE instruction_no = NEW.instruction_no AND product_no = NEW.product_no;

    UPDATE instruction
    SET progress_status = 1
    WHERE instruction_no = NEW.instruction_no;

    IF (SELECT COUNT(*) FROM product_instruction
        WHERE instruction_no = NEW.instruction_no
        AND remain_amount > 0) = 0 THEN
        UPDATE instruction
        SET progress_status = 2
        WHERE instruction_no = NEW.instruction_no;
    end if;
END;
//
DELIMITER ;

# Delivery 수정시 inistruction의 잔량, 진행 상태 변화 Trigger
# remain_amount = remain_amount + Old.amount - new.amount
DELIMITER //
CREATE TRIGGER update_remain_amount
    AFTER UPDATE ON delivery_instruction
    FOR EACH ROW
BEGIN
    UPDATE product_instruction
    SET remain_amount = remain_amount + OLD.amount - NEW.amount
    WHERE instruction_no = OLD.instruction_no AND product_no = OLD.product_no;

    IF  (SELECT COUNT(*) FROM product_instruction
         WHERE instruction_no = OLD.instruction_no
           AND remain_amount = amount) = (SELECT COUNT(*) FROM product_instruction
                                          WHERE instruction_no = OLD.instruction_no) THEN
        UPDATE instruction
        SET progress_status = 0
        WHERE instruction_no = OLD.instruction_no;
    END IF;
END;
DELIMITER ;

# Delivery 삭제시 instruction의 잔량, 진행 상태 변화 Trigger
drop trigger delete_restore_remain_amount;
DELIMITER //
CREATE TRIGGER delete_restore_remain_amount
    AFTER DELETE ON delivery_instruction
    FOR EACH ROW
BEGIN
    UPDATE product_instruction
    SET remain_amount = remain_amount + OLD.amount
    WHERE instruction_no = OLD.instruction_no AND product_no = OLD.product_no;

    UPDATE instruction
    SET progress_status = 1
    WHERE instruction_no = OLD.instruction_no;

    IF  (SELECT COUNT(*) FROM product_instruction
        WHERE instruction_no = OLD.instruction_no
        AND remain_amount = amount) = (SELECT COUNT(*) FROM product_instruction
                                                       WHERE instruction_no = OLD.instruction_no) THEN
        UPDATE instruction
        SET progress_status = 0
        WHERE instruction_no = OLD.instruction_no;
    END IF;
END;
DELIMITER ;