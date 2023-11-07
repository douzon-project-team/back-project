package com.douzon.blooming.delivery_instruction.repo;

import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeliveryInstructionRepository {

    @Insert("${insertQuery}")
    void insert(String insertQuery);

    @Select("SELECT I.instruction_no AS instructionNo," +
            "C.customer_name AS customerName," +
            "C.customer_no AS customerNo," +
            "E.name AS employeeName," +
            "I.instruction_date AS instructionDate," +
            "I.expiration_date AS expirationDate " +
            "FROM project.instruction I " +
            "INNER JOIN project.delivery_instruction DI ON I.instruction_no = DI.instruction_no " +
            "INNER JOIN project.employee E ON I.employee_no = E.employee_no " +
            "INNER JOIN project.customer C ON I.customer_no = C.customer_no " +
            "WHERE DI.delivery_no = #{deliveryNo}")
    List<ListInstructionDto> findInstructionsByDeliverNo(String deliveryNo);

    @Select("SELECT DI.product_no AS productNo, " +
            "P.product_name AS productName, " +
            "DI.amount AS amount " +
            "FROM project.delivery_instruction DI " +
            "INNER JOIN project.product P ON DI.product_no = P.product_no " +
            "WHERE DI.delivery_no = #{deliveryNo} AND DI.instruction_no = #{instructionNo}")
    List<GetInstructionDetailDto> findDeliveryDetail(String deliveryNo, String instructionNo);

    @Insert("INSERT INTO project.delivery_instruction " +
            "VALUES ('#{deliveryNo}', '#{instructionNo}', #{dto.productNo}, #{dto.amount})")
    void insertProduct(String deliveryNo, String instructionNo, @Param("dto") ProductInstructionDto product);

    @Update("UPDATE project.delivery_instruction SET amount = #{amount} " +
            "WHERE delivery_no = #{deliveryNo} AND instruction_no = #{instruction} AND product_no = #{dto.productNo}")
    void updateProduct(String deliveryNo, String instructionNo, @Param("dto") ProductInstructionDto product);

    @Delete("DELETE FROM project.delivery_instruction " +
            "WHERE delivery_no = #{deliveryNo} AND instruction_no = #{instructionNo} AND product_no = #{dto.productNo}")
    void deleteProduct(String deliveryNo, String instructionNo, @Param("dto") ProductInstructionDto product);
}
