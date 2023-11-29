
DROP TRIGGER IF EXISTS instruction_no_trigger;
CREATE TRIGGER instruction_no_trigger
    BEFORE INSERT
    ON instruction
    FOR EACH ROW
BEGIN
    SET NEW.instruction_no = concat('WO', substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                    lpad((SELECT IFNULL(MAX(SUBSTRING(instruction_no, 7, 6)) + 1, 1)
                                          FROM instruction), 6, '0'));
END;

DROP TRIGGER IF EXISTS delivery_no_trigger;
CREATE TRIGGER delivery_no_trigger
    BEFORE INSERT
    ON delivery
    FOR EACH ROW
BEGIN
    SET NEW.delivery_no = concat('MW', substring(DATE_FORMAT(NOW(), '%Y%m'), 3),
                                 lpad((SELECT IFNULL(MAX(SUBSTRING(delivery_no, 7, 6)) + 1, 1)
                                       FROM delivery), 6, '0'));
END;

DROP TRIGGER IF EXISTS update_remain_amount;
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

DROP TRIGGER IF EXISTS delete_restore_remain_amount;
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

DROP TRIGGER IF EXISTS insert_remain_amount;
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
    END IF;
END;