package com.douzon.blooming.product_instruction.repo;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProductInstructionRepository {

    @Insert("${insertQuery}")
    void insert(String insertQuery);


    @Select("SELECT PI.product_no as product_no, P.product_code as product_code, PI.amount as amount " +
            "FROM product_instruction PI INNER JOIN product P ON PI.product_no = P.product_no " +
            "WHERE PI.instruction_no = #{instructionNo}")
    List<ResponseProductInstructionDto> getProductList(String instructionNo);

    @Insert("INSERT INTO product_instruction VALUES (#{product.productNo},#{instructionNo}, #{product.amount})")
    void insertProduct(String instructionNo, @Param("product") ProductInstructionDto updateProduct);

    @Update("UPDATE product_instruction SET amount = #{product.amount} " +
            "WHERE instruction_no = #{instructionNo} AND product_no = #{product.productNo}")
    void updateProduct(String instructionNo, @Param("product") ProductInstructionDto updateProduct);

    @Delete("DELETE FROM product_instruction WHERE product_no = #{product.productNo} AND instruction_no = #{instructionNo}")
    void deleteProduct(String instructionNo, @Param("product") ProductInstructionDto updateProduct);
}
