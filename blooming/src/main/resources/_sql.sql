DROP DATABASE IF EXISTS project;

CREATE DATABASE IF NOT EXISTS project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;

USE project;
CREATE TABLE `employee`
(
    `employee_no` BIGINT      NOT NULL PRIMARY KEY,
    `id`          VARCHAR(20) NOT NULL UNIQUE,
    `password`    TEXT NOT NULL,
    `name`        VARCHAR(30) NOT NULL,
    `img`         TEXT        NULL,
    `role`        TINYINT(1)  NOT NULL DEFAULT 1,
    `tel`         VARCHAR(13) NULL,
    `email`       VARCHAR(30) NULL,
    `hide`        TINYINT(1)  DEFAULT 0
);

CREATE TABLE `todo`
(
    `todo_no`     BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `employee_no` BIGINT NOT NULL,
    `content`     TEXT   NOT NULL,
    `created`     DATE       DEFAULT NOW(),
    `checked`     TINYINT(1) DEFAULT 0
);


CREATE TABLE `product`
(
    `product_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `product_code` VARCHAR(6)  NOT NULL UNIQUE,
    `product_name` VARCHAR(45) NOT NULL,
    `standard`     TEXT        NOT NULL,
    `unit`         INT         NOT NULL,
    `weight`       INT         NOT NULL,
    `price`        INT         NOT NULL,
    `hide`         TINYINT(1)  DEFAULT 0,
    FULLTEXT INDEX idx_product_name (`product_name`)
);

INSERT INTO product (product_code, product_name, standard, unit, weight, price)
VALUES
    ('AP0001', 'Mac Book Air 13', '1000mm * 100mm', '20', 800, 3000000),
    ('AP0002', 'Mac Book Air 15', '1000mm * 100mm', '30', 1200, 2500000),
    ('AP0003', 'iPhone 15 Pro', '250mm * 110mm', '40', 1500, 3500000),
    ('AP0004', 'Apple Watch Series 9', '100mm * 60mm', '50', 1000, 2000000),
    ('AP0005', 'Air Pods Pro MagSafe', '100mm * 100mm', '60', 900, 1500000),
    ('AP0006', 'AirTag', '50mm * 50mm', '70', 600, 500000),
    ('AP0007', 'iPad Pro', '400mm * 250mm', '80', 1800, 4000000),
    ('AP0008', 'iPad Air', '400mm * 250mm', '90', 1400, 3000000),
    ('AP0009', 'iPad 10', '400mm * 250mm', '100', 1000, 2000000),
    ('AP0010', 'Air Pods Pro 2', '100mm * 100mm', '10', 700, 2500000),
    ('AP0011', 'Air Pods 3', '100mm * 100mm', '20', 800, 2800000),
    ('AP0012', 'Air Pods 2', '100mm * 100mm', '30', 900, 3200000),
    ('SS0001', 'Samsung Galaxy S21', '160mm * 75mm', '40', 1000, 1500000),
    ('SS0002', 'Samsung Galaxy Note 20', '165mm * 76mm', '50', 1100, 1800000),
    ('SS0003', 'Samsung Galaxy A72', '158mm * 72mm', '60', 1300, 2000000),
    ('SS0004', 'Samsung Galaxy A52', '155mm * 70mm', '70', 1400, 2200000),
    ('SS0005', 'Samsung Galaxy S20', '155mm * 73mm', '80', 1200, 1700000),
    ('SS0006', 'Samsung Galaxy S20 Ultra', '165mm * 80mm', '90', 1600, 2500000),
    ('SS0007', 'Samsung Galaxy A32', '160mm * 72mm', '100', 900, 1300000),
    ('SS0008', 'Samsung Galaxy Z Fold 3', '160mm * 175mm', '10', 2000, 4000000),
    ('SS0009', 'Samsung Galaxy Z Flip 3', '160mm * 75mm', '20', 1100, 2200000),
    ('SS0010', 'Samsung Galaxy S10 Lite', '159mm * 74mm', '30', 950, 1600000),
    ('SS0011', 'Samsung Galaxy A51', '158mm * 73mm', '40', 1200, 1900000),
    ('SS0012', 'Samsung Galaxy A21s', '160mm * 75mm', '50', 850, 1400000);

CREATE TABLE `customer`
(
    `customer_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `customer_code` VARCHAR(5)  NOT NULL UNIQUE,
    `customer_name` VARCHAR(10) NOT NULL,
    `customer_tel`  VARCHAR(13) NOT NULL,
    `ceo`           VARCHAR(10) NOT NULL,
    `sector`        VARCHAR(20) NOT NULL,
    `hide`          TINYINT(1)  DEFAULT 0,
    FULLTEXT INDEX idx_customer_name (`customer_name`),
    FULLTEXT INDEX idx_sector (`sector`)
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
    `amount`         INT         NOT NULL,
    `remain_amount`  INT         NOT NULL
);



CREATE TABLE `delivery`
(
    `delivery_no`     VARCHAR(12) PRIMARY KEY,
    `progress_status` TINYINT(1) NOT NULL DEFAULT 0,
    `delivery_date`   DATE       NOT NULL,
    `employee_no`     BIGINT     NOT NULL
);


CREATE TABLE `delivery_instruction`
(
    `delivery_no`    VARCHAR(12) NOT NULL,
    `instruction_no` VARCHAR(12) NOT NULL,
    `product_no`     BIGINT      NOT NULL,
    `amount`         INT         NOT NULL
);

CREATE TABLE `message`
(
    `message_no`   INT PRIMARY KEY AUTO_INCREMENT,
    `send_id`      INT NOT NULL,
    `send_name`    VARCHAR(30) NOT NULL,
    `target_id`    INT NOT NULL,
    `target_name`  VARCHAR(30) NOT NULL,
    `message`      TEXT NOT NULL,
    `send_time`    TEXT NOT NULL,
    `message_check` TINYINT(1) default 0
);

CREATE TABLE `employee_log`
(
    `log_no`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `target_no`   BIGINT      NULL,
    `type`        TINYINT(1)  NOT NULL
);

CREATE TABLE `product_log`
(
    `log_no`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `product_no`  BIGINT      NULL,
    `type`        TINYINT(1)  NOT NULL
);

CREATE TABLE `instruction_log`
(
    `log_no`         BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`     VARCHAR(20) NOT NULL,
    `date`           DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no`    BIGINT      NOT NULL,
    `instruction_no` VARCHAR(12) NULL,
    `type`           TINYINT(1)  NOT NULL
);

CREATE TABLE `delivery_log`
(
    `log_no`      BIGINT PRIMARY KEY AUTO_INCREMENT,
    `ip_address`  VARCHAR(20) NOT NULL,
    `date`        DATETIME    NOT NULL DEFAULT NOW(),
    `modifier_no` BIGINT      NOT NULL,
    `delivery_no` VARCHAR(12) NULL,
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
#                                                           `product_no`
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
#             )
#     ON DELETE CASCADE;
#
# ALTER TABLE `delivery_instruction`
#     ADD CONSTRAINT `FK_instruction_TO_delivery_instruction_1` FOREIGN KEY (
#                                                                            `instruction_no`
#         )
#         REFERENCES `instruction` (
#                                   `instruction_no`
#             );


# ALTER TABLE `instruction` ADD CONSTRAINT `FK_customer_TO_instruction_1` FOREIGN KEY (
#                                                                                      `customer_no`
#     )
#     REFERENCES `customer` (
#                            `customer_no`
#         );
#
# ALTER TABLE `instruction` ADD CONSTRAINT `FK_employee_TO_instruction_1` FOREIGN KEY (
#                                                                                      `employee_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `delivery` ADD CONSTRAINT `FK_employee_TO_delivery_1` FOREIGN KEY (
#                                                                                `employee_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
# ALTER TABLE `todo` ADD CONSTRAINT `FK_employee_TO_todo_1` FOREIGN KEY (
#                                                                                `employee_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `employee_log` ADD CONSTRAINT `FK_employee_TO_employee_log_1` FOREIGN KEY (
#                                                                                        `modifier_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `employee_log` ADD CONSTRAINT `FK_employee_TO_employee_log_2` FOREIGN KEY (
#                                                                                        `target_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `product_log` ADD CONSTRAINT `FK_employee_TO_product_log_1` FOREIGN KEY (
#                                                                                      `modifier_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `product_log` ADD CONSTRAINT `FK_product_TO_product_log_1` FOREIGN KEY (
#                                                                                     `product_no`
#     )
#     REFERENCES `product` (
#                           `product_no`
#         );
#
# ALTER TABLE `instruction_log` ADD CONSTRAINT `FK_employee_TO_instruction_log_1` FOREIGN KEY (
#                                                                                              `modifier_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `instruction_log` ADD CONSTRAINT `FK_instruction_TO_instruction_log_1` FOREIGN KEY (
#                                                                                                 `instruction_no`
#     )
#     REFERENCES `instruction` (
#                               `instruction_no`
#         );
#
# ALTER TABLE `delivery_log` ADD CONSTRAINT `FK_employee_TO_delivery_log_1` FOREIGN KEY (
#                                                                                        `modifier_no`
#     )
#     REFERENCES `employee` (
#                            `employee_no`
#         );
#
# ALTER TABLE `delivery_log` ADD CONSTRAINT `FK_delivery_TO_delivery_log_1` FOREIGN KEY (
#                                                                                        `delivery_no`
#     )
#     REFERENCES `delivery` (
#                            `delivery_no`
#         );


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
DELIMITER //
CREATE TRIGGER delivery_no_trigger
    BEFORE INSERT
    ON delivery
    FOR EACH ROW
BEGIN
    SET NEW.delivery_no = concat('MW', substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                 lpad((SELECT IFNULL(MAX(SUBSTRING(delivery_no, 7, 6)) + 1, 1)
                                       FROM delivery), 6, '0'));
END;
//
DELIMITER ;


# Delivery 수정시 instruction의 잔량, 진행 상태 변화 Trigger
DELIMITER //
CREATE TRIGGER update_remain_amount
    AFTER UPDATE
    ON delivery_instruction
    FOR EACH ROW
BEGIN
    UPDATE product_instruction
    SET remain_amount = remain_amount + OLD.amount - NEW.amount
    WHERE instruction_no = OLD.instruction_no
      AND product_no = OLD.product_no;

    IF (SELECT COUNT(*)
        FROM product_instruction
        WHERE instruction_no = OLD.instruction_no
          AND remain_amount = amount) = (SELECT COUNT(*)
                                         FROM product_instruction
                                         WHERE instruction_no = OLD.instruction_no) THEN
        UPDATE instruction
        SET progress_status = 0
        WHERE instruction_no = OLD.instruction_no;
    END IF;
END;
DELIMITER ;

# Delivery 삭제시 instruction의 잔량, 진행 상태 변화 Trigger
DELIMITER //
CREATE TRIGGER delete_restore_remain_amount
    AFTER DELETE
    ON delivery_instruction
    FOR EACH ROW
BEGIN
    UPDATE product_instruction
    SET remain_amount = remain_amount + OLD.amount
    WHERE instruction_no = OLD.instruction_no
      AND product_no = OLD.product_no;

    UPDATE instruction
    SET progress_status = 1
    WHERE instruction_no = OLD.instruction_no;

    IF (SELECT COUNT(*)
        FROM product_instruction
        WHERE instruction_no = OLD.instruction_no
          AND remain_amount = amount) = (SELECT COUNT(*)
                                         FROM product_instruction
                                         WHERE instruction_no = OLD.instruction_no) THEN
        UPDATE instruction
        SET progress_status = 0
        WHERE instruction_no = OLD.instruction_no;
    END IF;
END;
DELIMITER ;

INSERT
INTO customer(customer_no, customer_code, customer_name, customer_tel, ceo, sector)
VALUES (1, 'C0001', '더존 비즈온', '010-1234-1234', '김용우', '컴퓨터 서비스업'),
       (2, 'C0002', '삼성 전자', '010-3214-3214', '경계현', '이동전화기 제조업'),
       (3, 'C0003', 'HMM', '010-6254-5164', '김경배', '외항 화물 운송업'),
       (4, 'C0004', '현대 자동차', '010-2634-5134', '장재훈', '자동차 제조업'),
       (5, 'C0005', 'LG 화학', '010-5362-7098', '신학철', '물질 제조업'),
       (6, 'C0006', 'SK 하이닉스', '010-1654-1978', '곽노정', '발광 다이오드 제조업'),
       (7, 'C0007', '기업 은행', '010-1436-5541', '김성태', '국내은행'),
       (8, 'C0008', 'S-Oil', '010-1435-7908', '안와르에이알히즈아지', '원유 정제처리업'),
       (9, 'C0009', '기아 자동차', '010-1503-8764', '최준영', '차량 제조,판매,정비'),
       (10, 'C0010', '대한 항공', '010-1063-7948', '조원태', '항공 여객 운송업'),
       (11, 'C0011', 'KB 금융', '010-7816-9341', '윤종규', '지주회사'),
       (12, 'C0012', '카카오', '010-9340-5687', '홍은택', '인터넷 정보매개 서비스업'),
       (13, 'C0013', '현대 모비스', '010-0986-7453', '정의선', '신품 부품 제조업'),
       (14, 'C0014', '삼성 물산', '010-9817-5631', '고정석', '직물 도매업'),
       (15, 'C0015', '신한 지주', '010-9801-1346', '진옥동', '지주회사'),
       (16, 'C0016', '현대 글로비스', '010-9283-6923', '이규복', '화물운송 관련 서비스업'),
       (17, 'C0017', '우리 금융 지주', '010-8197-4981', '임종룡', '지주회사'),
       (18, 'C0018', '하나 금융 지주', '010-1894-7132', '함영주', '지주회사');


insert into project.instruction (instruction_no, instruction_date, customer_no, employee_no, progress_status, expiration_date)
values  ('WO2312000001', '2021-10-13', 1, 200002, 2, '2021-11-16'),
        ('WO2312000002', '2021-11-03', 6, 200002, 2, '2021-12-03'),
        ('WO2312000003', '2021-11-09', 1, 200002, 2, '2021-12-09'),
        ('WO2312000004', '2021-11-18', 5, 200002, 2, '2021-12-18'),
        ('WO2312000005', '2021-11-10', 9, 200002, 2, '2021-12-18'),
        ('WO2312000006', '2021-11-11', 6, 200002, 2, '2021-12-18'),
        ('WO2312000007', '2021-11-23', 1, 200002, 2, '2021-12-23'),
        ('WO2312000008', '2021-11-13', 7, 200002, 2, '2021-12-23'),
        ('WO2312000009', '2021-11-25', 2, 200002, 2, '2021-12-25'),
        ('WO2312000010', '2021-12-03', 1, 200002, 2, '2021-12-25'),
        ('WO2312000011', '2021-12-06', 15, 200002, 2, '2021-12-25'),
        ('WO2312000012', '2021-12-09', 11, 200002, 2, '2021-12-25'),
        ('WO2312000013', '2021-12-13', 13, 200002, 2, '2022-01-13'),
        ('WO2312000014', '2021-12-15', 14, 200002, 2, '2022-01-13'),
        ('WO2312000015', '2021-12-21', 2, 200002, 2, '2022-01-13'),
        ('WO2312000016', '2021-12-23', 1, 200002, 2, '2022-01-23'),
        ('WO2312000017', '2022-01-04', 5, 200002, 2, '2022-02-04'),
        ('WO2312000018', '2022-01-18', 1, 200002, 2, '2022-02-04'),
        ('WO2312000019', '2022-01-26', 10, 200002, 2, '2022-02-26'),
        ('WO2312000020', '2022-02-02', 2, 200002, 2, '2022-02-26'),
        ('WO2312000021', '2022-02-08', 1, 200002, 2, '2022-03-08'),
        ('WO2312000022', '2022-02-23', 8, 200002, 2, '2022-03-08'),
        ('WO2312000023', '2022-03-23', 4, 200002, 2, '2022-03-08'),
        ('WO2312000024', '2022-03-31', 3, 200002, 2, '2022-03-08'),
        ('WO2312000025', '2022-04-19', 7, 200002, 2, '2022-05-19'),
        ('WO2312000026', '2022-04-27', 12, 200002, 2, '2022-05-27'),
        ('WO2312000027', '2022-05-17', 11, 200002, 2, '2022-06-17'),
        ('WO2312000028', '2022-05-23', 17, 200002, 2, '2022-06-17'),
        ('WO2312000029', '2022-06-08', 16, 200002, 2, '2022-07-08'),
        ('WO2312000030', '2022-07-01', 5, 200002, 2, '2022-08-01'),
        ('WO2312000031', '2022-07-12', 7, 200002, 2, '2022-08-01'),
        ('WO2312000032', '2022-07-28', 6, 200002, 2, '2022-08-28'),
        ('WO2312000033', '2022-08-17', 2, 200002, 2, '2022-08-28'),
        ('WO2312000034', '2022-08-30', 1, 200002, 2, '2022-09-30'),
        ('WO2312000035', '2022-09-21', 10, 200002, 2, '2022-10-21'),
        ('WO2312000036', '2022-11-09', 14, 200002, 2, '2022-12-09'),
        ('WO2312000037', '2023-10-03', 11, 200002, 0, '2023-11-03'),
        ('WO2312000038', '2023-10-06', 13, 200002, 0, '2023-11-03'),
        ('WO2312000039', '2023-10-12', 11, 200002, 0, '2023-11-03'),
        ('WO2312000040', '2023-10-17', 5, 200002, 0, '2023-11-17'),
        ('WO2312000041', '2023-11-02', 4, 200002, 0, '2023-12-02'),
        ('WO2312000042', '2023-11-10', 1, 200002, 0, '2023-12-02'),
        ('WO2312000043', '2023-10-31', 4, 200002, 0, '2023-12-01'),
        ('WO2312000044', '2023-11-17', 10, 200002, 0, '2023-12-17'),
        ('WO2312000045', '2023-11-21', 2, 200002, 0, '2023-12-21'),
        ('WO2312000046', '2023-12-02', 3, 200002, 0, '2023-12-21'),
        ('WO2312000047', '2023-12-26', 4, 200002, 0, '2024-01-26'),
        ('WO2312000048', '2023-12-26', 7, 200002, 0, '2024-01-26'),
        ('WO2312000049', '2023-12-27', 6, 200002, 0, '2024-01-27'),
        ('WO2312000050', '2023-12-27', 9, 200002, 0, '2024-01-27'),
        ('WO2312000051', '2023-12-27', 1, 200002, 0, '2024-01-27'),
        ('WO2312000052', '2023-12-28', 8, 200002, 0, '2024-01-27'),
        ('WO2312000053', '2023-12-28', 2, 200002, 0, '2024-01-28'),
        ('WO2312000054', '2023-12-28', 7, 200002, 0, '2024-01-28'),
        ('WO2312000055', '2023-12-29', 1, 200002, 0, '2024-01-28'),
        ('WO2312000056', '2023-12-28', 6, 200002, 0, '2024-01-28'),
        ('WO2312000057', '2023-12-29', 15, 200002, 0, '2024-01-28'),
        ('WO2312000058', '2023-12-29', 17, 200002, 0, '2024-01-28'),
        ('WO2312000059', '2023-12-30', 12, 200002, 0, '2024-01-28'),
        ('WO2312000060', '2023-12-30', 13, 200002, 0, '2024-01-28'),
        ('WO2312000061', '2023-12-30', 5, 200002, 0, '2024-01-28'),
        ('WO2312000062', '2023-12-26', 4, 200002, 0, '2024-01-26'),
        ('WO2312000063', '2023-12-27', 8, 200002, 0, '2024-01-26'),
        ('WO2312000064', '2024-01-03', 9, 200002, 0, '2024-02-03'),
        ('WO2312000065', '2024-01-05', 1, 200002, 0, '2024-02-03'),
        ('WO2312000066', '2024-01-23', 4, 200002, 0, '2024-02-23'),
        ('WO2312000067', '2024-02-08', 6, 200002, 0, '2024-02-23'),
        ('WO2312000068', '2024-02-20', 4, 200002, 0, '2024-02-23'),
        ('WO2312000069', '2025-02-12', 1, 200002, 0, '2025-03-12'),
        ('WO2312000070', '2025-02-18', 10, 200002, 0, '2025-03-12');

insert into project.product_instruction (product_no, instruction_no, amount, remain_amount)
values  (3, 'WO2312000001', 15, 25),
        (5, 'WO2312000002', 25, 25),
        (5, 'WO2312000003', 15, 15),
        (4, 'WO2312000004', 25, 25),
        (6, 'WO2312000005', 25, 25),
        (8, 'WO2312000006', 25, 25),
        (5, 'WO2312000007', 25, 25),
        (6, 'WO2312000008', 22, 22),
        (5, 'WO2312000009', 15, 15),
        (2, 'WO2312000010', 15, 15),
        (4, 'WO2312000011', 25, 25),
        (2, 'WO2312000012', 25, 25),
        (5, 'WO2312000013', 25, 25),
        (17, 'WO2312000014', 25, 25),
        (12, 'WO2312000015', 15, 15),
        (14, 'WO2312000016', 15, 15),
        (6, 'WO2312000017', 25, 15),
        (7, 'WO2312000018', 26, 26),
        (5, 'WO2312000019', 25, 25),
        (5, 'WO2312000020', 25, 25),
        (7, 'WO2312000021', 25, 25),
        (6, 'WO2312000022', 15, 15),
        (5, 'WO2312000023', 15, 15),
        (9, 'WO2312000024', 15, 15),
        (6, 'WO2312000025', 25, 25),
        (15, 'WO2312000026', 25, 25),
        (8, 'WO2312000027', 15, 15),
        (16, 'WO2312000028', 155, 155),
        (8, 'WO2312000029', 155, 155),
        (20, 'WO2312000030', 15, 15),
        (7, 'WO2312000031', 25, 25),
        (15, 'WO2312000032', 15, 15),
        (4, 'WO2312000033', 25, 25),
        (15, 'WO2312000034', 31, 31),
        (10, 'WO2312000035', 25, 25),
        (17, 'WO2312000036', 25, 25),
        (6, 'WO2312000037', 15, 15),
        (16, 'WO2312000038', 15, 15),
        (7, 'WO2312000039', 15, 15),
        (3, 'WO2312000040', 15, 15),
        (15, 'WO2312000041', 5, 5),
        (6, 'WO2312000042', 15, 15),
        (16, 'WO2312000043', 25, 25),
        (4, 'WO2312000044', 15, 15),
        (17, 'WO2312000045', 15, 15),
        (2, 'WO2312000046', 15, 15),
        (16, 'WO2312000047', 15, 15),
        (4, 'WO2312000048', 25, 25),
        (14, 'WO2312000049', 25, 25),
        (7, 'WO2312000050', 25, 25),
        (3, 'WO2312000050', 15, 15),
        (14, 'WO2312000051', 25, 25),
        (16, 'WO2312000051', 15, 15),
        (2, 'WO2312000052', 25, 25),
        (4, 'WO2312000052', 15, 15),
        (18, 'WO2312000053', 25, 25),
        (17, 'WO2312000053', 15, 15),
        (8, 'WO2312000054', 25, 25),
        (16, 'WO2312000055', 25, 25),
        (17, 'WO2312000055', 15, 15),
        (4, 'WO2312000056', 15, 15),
        (8, 'WO2312000056', 25, 25),
        (16, 'WO2312000057', 25, 25),
        (18, 'WO2312000057', 30, 30),
        (10, 'WO2312000058', 15, 15),
        (15, 'WO2312000059', 25, 25),
        (18, 'WO2312000059', 40, 40),
        (5, 'WO2312000060', 25, 25),
        (4, 'WO2312000060', 15, 15),
        (18, 'WO2312000061', 10, 10),
        (17, 'WO2312000061', 5, 5),
        (4, 'WO2312000062', 15, 15),
        (16, 'WO2312000063', 15, 15),
        (18, 'WO2312000063', 25, 25),
        (4, 'WO2312000064', 25, 25),
        (4, 'WO2312000065', 15, 15),
        (6, 'WO2312000066', 25, 25),
        (6, 'WO2312000067', 15, 15),
        (7, 'WO2312000068', 25, 25),
        (7, 'WO2312000069', 15, 15),
        (9, 'WO2312000070', 15, 15);

insert into project.delivery (delivery_no, progress_status, delivery_date, employee_no)
values  ('MW2312000001', 1, '2021-11-03', 200003),
        ('MW2312000002', 1, '2021-11-09', 200003),
        ('MW2312000003', 1, '2021-11-12', 200003),
        ('MW2312000004', 1, '2021-11-15', 200003),
        ('MW2312000005', 1, '2021-12-01', 200003),
        ('MW2312000006', 1, '2021-12-09', 200003),
        ('MW2312000007', 1, '2021-12-22', 200002),
        ('MW2312000008', 1, '2021-12-31', 200002),
        ('MW2312000009', 1, '2022-01-28', 200003),
        ('MW2312000010', 1, '2022-02-21', 200003),
        ('MW2312000011', 1, '2022-03-09', 200002),
        ('MW2312000012', 1, '2022-04-21', 200002),
        ('MW2312000013', 1, '2022-05-03', 200002),
        ('MW2312000014', 1, '2022-05-26', 200002),
        ('MW2312000015', 1, '2022-06-29', 200003),
        ('MW2312000016', 1, '2022-07-26', 200003),
        ('MW2312000017', 1, '2022-08-26', 200002),
        ('MW2312000018', 1, '2022-09-15', 200003),
        ('MW2312000019', 1, '2022-10-25', 200002),
        ('MW2312000020', 1, '2022-11-23', 200003),
        ('MW2312000021', 1, '2023-10-11', 200003),
        ('MW2312000022', 1, '2023-10-27', 200002),
        ('MW2312000023', 1, '2023-11-15', 200003),
        ('MW2312000024', 1, '2023-11-30', 200002),
        ('MW2312000025', 1, '2023-12-04', 200003),
        ('MW2312000026', 0, '2023-12-26', 200003),
        ('MW2312000027', 0, '2023-12-26', 200002),
        ('MW2312000028', 0, '2023-12-27', 200003),
        ('MW2312000029', 0, '2023-12-28', 200002),
        ('MW2312000030', 0, '2023-12-29', 200003),
        ('MW2312000031', 0, '2023-12-29', 200002),
        ('MW2312000032', 0, '2023-12-30', 200002);

# Delivery 등록시 instruction의 잔량, 진행 상태 변화 Trigger
DELIMITER //
CREATE TRIGGER insert_remain_amount
    AFTER INSERT
    ON delivery_instruction
    FOR EACH ROW
BEGIN
    DECLARE new_remain_amount INT;
    SET new_remain_amount = (SELECT remain_amount - NEW.amount
                             FROM product_instruction
                             WHERE instruction_no = NEW.instruction_no
                               AND product_no = NEW.product_no);

    UPDATE product_instruction
    SET remain_amount = new_remain_amount
    WHERE instruction_no = NEW.instruction_no
      AND product_no = NEW.product_no;

    UPDATE instruction
    SET progress_status = 1
    WHERE instruction_no = NEW.instruction_no;

    IF (SELECT COUNT(*)
        FROM product_instruction
        WHERE instruction_no = NEW.instruction_no
          AND remain_amount > 0) = 0 THEN
        UPDATE instruction
        SET progress_status = 2
        WHERE instruction_no = NEW.instruction_no;
    end if;
END;
//
DELIMITER ;

insert into project.delivery_instruction (delivery_no, instruction_no, product_no, amount)
values  ('MW2312000001', 'WO2312000001', 3, 15),
        ('MW2312000002', 'WO2312000002', 5, 25),
        ('MW2312000002', 'WO2312000003', 5, 15),
        ('MW2312000003', 'WO2312000004', 4, 25),
        ('MW2312000003', 'WO2312000005', 6, 25),
        ('MW2312000004', 'WO2312000006', 8, 25),
        ('MW2312000004', 'WO2312000008', 6, 22),
        ('MW2312000005', 'WO2312000007', 5, 25),
        ('MW2312000005', 'WO2312000009', 5, 15),
        ('MW2312000006', 'WO2312000010', 2, 15),
        ('MW2312000006', 'WO2312000011', 4, 25),
        ('MW2312000007', 'WO2312000012', 2, 25),
        ('MW2312000007', 'WO2312000013', 5, 25),
        ('MW2312000008', 'WO2312000014', 17, 25),
        ('MW2312000008', 'WO2312000015', 12, 15),
        ('MW2312000008', 'WO2312000016', 14, 15),
        ('MW2312000009', 'WO2312000017', 6, 25),
        ('MW2312000009', 'WO2312000018', 7, 26),
        ('MW2312000010', 'WO2312000019', 5, 25),
        ('MW2312000010', 'WO2312000020', 5, 25),
        ('MW2312000010', 'WO2312000021', 7, 25),
        ('MW2312000011', 'WO2312000022', 6, 15),
        ('MW2312000012', 'WO2312000023', 5, 15),
        ('MW2312000012', 'WO2312000024', 9, 15),
        ('MW2312000013', 'WO2312000025', 6, 25),
        ('MW2312000013', 'WO2312000026', 15, 25),
        ('MW2312000014', 'WO2312000027', 8, 15),
        ('MW2312000014', 'WO2312000028', 16, 155),
        ('MW2312000015', 'WO2312000029', 8, 155),
        ('MW2312000016', 'WO2312000030', 20, 15),
        ('MW2312000016', 'WO2312000031', 7, 25),
        ('MW2312000017', 'WO2312000032', 15, 15),
        ('MW2312000017', 'WO2312000033', 4, 25),
        ('MW2312000018', 'WO2312000034', 15, 31),
        ('MW2312000019', 'WO2312000035', 10, 25),
        ('MW2312000020', 'WO2312000036', 17, 25),
        ('MW2312000021', 'WO2312000037', 6, 15),
        ('MW2312000021', 'WO2312000038', 16, 15),
        ('MW2312000022', 'WO2312000039', 7, 15),
        ('MW2312000022', 'WO2312000040', 3, 15),
        ('MW2312000023', 'WO2312000041', 15, 5),
        ('MW2312000023', 'WO2312000042', 6, 15),
        ('MW2312000024', 'WO2312000043', 16, 25),
        ('MW2312000024', 'WO2312000044', 4, 15),
        ('MW2312000024', 'WO2312000045', 17, 15),
        ('MW2312000025', 'WO2312000046', 2, 15),
        ('MW2312000026', 'WO2312000047', 16, 10),
        ('MW2312000027', 'WO2312000048', 4, 25),
        ('MW2312000028', 'WO2312000049', 14, 15),
        ('MW2312000028', 'WO2312000050', 7, 15),
        ('MW2312000029', 'WO2312000050', 3, 10),
        ('MW2312000030', 'WO2312000052', 2, 15),
        ('MW2312000031', 'WO2312000052', 4, 10),
        ('MW2312000031', 'WO2312000053', 18, 25),
        ('MW2312000031', 'WO2312000053', 17, 15);


DELIMITER //
CREATE TRIGGER before_delete_delivery
    BEFORE DELETE
    ON delivery
    FOR EACH ROW
BEGIN
    -- 여기에 삭제되는 행과 관련된 delivery_instruction 행에 대한 작업을 수행
    DELETE FROM delivery_instruction WHERE delivery_no = OLD.delivery_no;
END;
//
DELIMITER ;