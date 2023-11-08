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

    @Select("SELECT I.instruction_no AS instruction_no," +
            "C.customer_name AS customer_name," +
            "C.customer_no AS customer_no," +
            "E.name AS employee_name," +
            "I.instruction_date AS instruction_date," +
            "I.expiration_date AS expiration_date," +
            "I.progress_status AS progress_status " +
            "FROM project.instruction I " +
            "INNER JOIN project.delivery_instruction DI ON I.instruction_no = DI.instruction_no " +
            "INNER JOIN project.employee E ON I.employee_no = E.employee_no " +
            "INNER JOIN project.customer C ON I.customer_no = C.customer_no " +
            "WHERE DI.delivery_no = #{deliveryNo} " +
            "GROUP BY I.instruction_no")
    List<ListInstructionDto> findInstructionsByDeliverNo(String deliveryNo);

    @Select("SELECT DI.product_no AS product_no, " +
            "P.product_code AS product_code, " +
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
