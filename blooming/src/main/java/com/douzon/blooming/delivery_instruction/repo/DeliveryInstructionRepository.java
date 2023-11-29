package com.douzon.blooming.delivery_instruction.repo;

import com.douzon.blooming.delivery_instruction.dto.request.DeleteDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.response.DeliveryListInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeliveryInstructionRepository {

    @Insert("${insertQuery}")
    void insert(String insertQuery);

    @Select("SELECT I.instruction_no AS instruction_no," +
            "E.name AS employee_name," +
            "C.customer_name AS customer_name," +
            "I.instruction_date AS instruction_date," +
            "I.expiration_date AS expiration_date," +
            "P.product_no AS product_no," +
            "P.product_code AS product_code," +
            "P.product_name AS product_name," +
            "DI.amount AS amount " +
            "FROM project.delivery_instruction DI " +
            "LEFT JOIN project.instruction I ON I.instruction_no = DI.instruction_no " +
            "INNER JOIN project.employee E ON I.employee_no = E.employee_no " +
            "INNER JOIN project.customer C ON I.customer_no = C.customer_no " +
            "INNER JOIN project.product P ON DI.product_no = P.product_no " +
            "WHERE DI.delivery_no = #{deliveryNo} ")
    List<DeliveryListInstructionDto> findInstructionsByDeliverNo(String deliveryNo);

    @Select("SELECT COUNT(DISTINCT(instruction_no)) " +
            "FROM project.delivery_instruction " +
            "WHERE delivery_no = #{deliveryNo} ")
    Integer getInstructionCount(String deliveryNo);

    @Insert("INSERT INTO project.delivery_instruction " +
            "VALUES (#{deliveryNo}, #{instructionNo}, #{dto.productNo}, #{dto.amount})")
    void insertProduct(String deliveryNo, String instructionNo, @Param("dto") ProductInstructionDto product);

    @Update("UPDATE project.delivery_instruction SET amount = #{amount} " +
            "WHERE delivery_no = #{deliveryNo} " +
            "AND instruction_no = #{instructionNo} " +
            "AND product_no = #{productNo}")
    Integer updateProduct(String deliveryNo, String instructionNo, Long productNo, Integer amount);

    @Delete("DELETE FROM project.delivery_instruction " +
            "WHERE delivery_no = #{deliveryNo} " +
            "AND instruction_no = #{instructionNo} " +
            "AND product_no = #{productNo}")
    Integer deleteProduct(String deliveryNo, String instructionNo, String productNo);
}