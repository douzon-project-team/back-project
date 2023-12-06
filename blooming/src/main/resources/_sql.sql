DROP DATABASE IF EXISTS project;

CREATE DATABASE IF NOT EXISTS project CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;

USE project;
CREATE TABLE `employee`
(
    `employee_no` BIGINT      NOT NULL PRIMARY KEY,
    `id`          VARCHAR(20) NOT NULL UNIQUE,
    `password`    VARCHAR(20) NOT NULL,
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

INSERT INTO `todo` (`employee_no`, `content`)
VALUES (200001, 'TODO 1'),
       (200001, 'TODO 2'),
       (200001, 'TODO 3'),
       (200001, 'TODO 4'),
       (200001, 'TODO 5'),
       (200001, 'TODO 6'),
       (200001, 'TODO 7');

CREATE TABLE `product`
(
    `product_no`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `product_code` VARCHAR(6)  NOT NULL UNIQUE,
    `product_name` VARCHAR(45) NOT NULL,
    `standard`     TEXT        NOT NULL,
    `unit`         INT         NOT NULL,
    `weight`       INT         NOT NULL,
    `price`        INT         NOT NULL,
    `hide`         TINYINT(1)  DEFAULT 0
);

INSERT INTO product (product_code, product_name, standard, unit, weight, price)
VALUES
    ('AP0001', 'MacBook Air 13', '1000mm * 100mm', '20', 800, 3000000),
    ('AP0002', 'MacBook Air 15', '1000mm * 100mm', '30', 1200, 2500000),
    ('AP0003', 'iPhone 15 Pro', '250mm * 110mm', '40', 1500, 3500000),
    ('AP0004', 'Apple Watch Series 9', '100mm * 60mm', '50', 1000, 2000000),
    ('AP0005', 'AirPods Pro MagSafe', '100mm * 100mm', '60', 900, 1500000),
    ('AP0006', 'AirTag', '50mm * 50mm', '70', 600, 500000),
    ('AP0007', 'iPad Pro', '400mm * 250mm', '80', 1800, 4000000),
    ('AP0008', 'iPad Air', '400mm * 250mm', '90', 1400, 3000000),
    ('AP0009', 'iPad 10', '400mm * 250mm', '100', 1000, 2000000),
    ('AP0010', 'AirPods Pro 2', '100mm * 100mm', '10', 700, 2500000),
    ('AP0011', 'AirPods 3', '100mm * 100mm', '20', 800, 2800000),
    ('AP0012', 'AirPods 2', '100mm * 100mm', '30', 900, 3200000),
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
    `hide`          TINYINT(1)  DEFAULT 0
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
    `messageCheck` TINYINT(1) default 0
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
INSERT INTO `employee` (employee_no, id, password, name, img, role, tel, email)
VALUES (200001, 'admin', '1234', '관리자', '4bce5389-8191-46f8-ad91-a5ee197ee837.png', 0,
        '010-1111-2222', 'admin@admin.com'),
       (200002, 'm1', '1234', '박상웅', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200003, 'm2', '1234', '송재근', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200004, 'm3', '1234', '윤찬웅', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200005, 'm4', '1234', '오수민', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200006, 'm5', '1234', '김철수', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200007, 'm6', '1234', '박한나', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200008, 'm7', '1234', '김지철', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200009, 'm9', '1234', '한지민', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com'),
       (200010, 'm10', '1234', '이지훈', '4287e7f7-17a8-4710-b32f-d21255ea53e2.png', 1,
        '010-1111-2222', 'member@member.com');

INSERT
INTO customer(customer_no, customer_code, customer_name, customer_tel, ceo, sector)
VALUES (1, 'C0001', '더존비즈온', '010-1234-1234', '김용우', '컴퓨터 서비스업'),
       (2, 'C0002', '삼성전자', '010-3214-3214', '경계현', '이동전화기 제조업'),
       (3, 'C0003', 'HMM', '010-6254-5164', '김경배', '외항 화물 운송업'),
       (4, 'C0004', '현대차', '010-2634-5134', '장재훈', '자동차 제조업'),
       (5, 'C0005', 'LG화학', '010-5362-7098', '신학철', '물질 제조업'),
       (6, 'C0006', 'SK하이닉스', '010-1654-1978', '곽노정', '발광 다이오드 제조업'),
       (7, 'C0007', '기업은행', '010-1436-5541', '김성태', '국내은행'),
       (8, 'C0008', 'S-Oil', '010-1435-7908', '안와르에이알히즈아지', '원유 정제처리업'),
       (9, 'C0009', '기아', '010-1503-8764', '최준영', '차량 제조,판매,정비'),
       (10, 'C0010', '대한항공', '010-1063-7948', '조원태', '항공 여객 운송업'),
       (11, 'C0011', 'KB금융', '010-7816-9341', '윤종규', '지주회사'),
       (12, 'C0012', '카카오', '010-9340-5687', '홍은택', '인터넷 정보매개 서비스업'),
       (13, 'C0013', '현대모비스', '010-0986-7453', '정의선', '신품 부품 제조업'),
       (14, 'C0014', '삼성물산', '010-9817-5631', '고정석', '직물 도매업'),
       (15, 'C0015', '신한지주', '010-9801-1346', '진옥동', '지주회사'),
       (16, 'C0016', '현대글로비스', '010-9283-6923', '이규복', '화물운송 관련 서비스업'),
       (17, 'C0017', '우리금융지주', '010-8197-4981', '임종룡', '지주회사'),
       (18, 'C0018', '하나금융지주', '010-1894-7132', '함영주', '지주회사');


INSERT INTO instruction(instruction_date, customer_no, employee_no, expiration_date,
                        progress_status)
VALUES ('2023-10-22', 3, 200003, '2023-11-21', 0),
       ('2023-10-25', 2, 200005, '2023-11-25', 0),
       ('2023-10-25', 2, 200003, '2023-11-25', 0),
       ('2022-01-25', 10, 200003, '2023-11-25', 0),
       ('2021-02-25', 16, 200003, '2023-11-25', 0),
       ('2020-11-25', 15, 200005, '2023-11-25', 1),
       ('2023-12-25', 18, 200002, '2023-11-25', 1),
       ('2023-09-21', 11, 200005, '2023-11-25', 1),
       ('2021-10-30', 4, 200005, '2023-11-25', 2),
       ('2023-10-25', 5, 200005, '2023-11-25', 2),
       ('2020-07-25', 4, 200002, '2023-12-25', 2),
       ('2023-11-25', 4, 200002, '2023-12-25', 1),
       ('2023-10-12', 6, 200004, '2023-12-25', 0),
       ('2022-05-10', 8, 200004, '2023-12-25', 1),
       ('2022-10-25', 10, 200004, '2023-12-25', 2);

INSERT INTO delivery(delivery_date, employee_no)
VALUES ('2023-11-29', 200004);

INSERT INTO product_instruction
VALUES (1, 'WO2311000001', 15, 15),
       (5, 'WO2311000001', 20, 20),
       (2, 'WO2311000002', 20, 20),
       (7, 'WO2311000002', 30, 30),
       (10, 'WO2311000002', 20, 20),
       (11, 'WO2311000002', 20, 20),
       (12, 'WO2311000002', 20, 20),
       (13, 'WO2311000002', 20, 20),
       (10, 'WO2311000003', 15, 15),
       (11, 'WO2311000003', 15, 15),
       (1, 'WO2311000003', 15, 15),
       (8, 'WO2311000003', 15, 15),
       (1, 'WO2311000004', 30, 30),
       (2, 'WO2311000004', 100, 100),
       (3, 'WO2311000004', 30, 30),
       (4, 'WO2311000004', 20, 20),
       (5, 'WO2311000004', 30, 30),
       (6, 'WO2311000004', 10, 10),
       (3, 'WO2311000005', 25, 25),
       (4, 'WO2311000006', 12, 12),
       (15, 'WO2311000007', 18, 18),
       (6, 'WO2311000008', 40, 40),
       (7, 'WO2311000009', 35, 35),
       (8, 'WO2311000010', 11, 11),
       (9, 'WO2311000011', 20, 20),
       (11, 'WO2311000012', 28, 28),
       (12, 'WO2311000013', 16, 16),
       (13, 'WO2311000014', 19, 19),
       (14, 'WO2311000015', 22, 22),
       (5, 'WO2311000006', 13, 13),
       (16, 'WO2311000007', 17, 17),
       (17, 'WO2311000008', 24, 24),
       (18, 'WO2311000009', 32, 32),
       (19, 'WO2311000015', 21, 21),
       (20, 'WO2311000014', 26, 26),
       (21, 'WO2311000013', 37, 37),
       (22, 'WO2311000012', 31, 31),
       (23, 'WO2311000011', 23, 23);

INSERT INTO delivery_instruction
VALUES ('MW2311000001', 'WO2311000001', 1, 10),
       ('MW2311000001', 'WO2311000001', 2, 15),
       ('MW2311000001', 'WO2311000002', 1, 5),
       ('MW2311000001', 'WO2311000002', 2, 10);

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