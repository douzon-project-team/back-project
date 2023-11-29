package com.douzon.blooming.product_instruction.repo;

import com.douzon.blooming.product_instruction.dto.request.AddProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.DeleteProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.UpdateProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import java.util.List;

import com.douzon.blooming.product_instruction.dto.response.ResponseProductRemainAmountDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface ProductInstructionRepository {

  @Insert("INSERT INTO project.product_instruction (product_no, instruction_no, amount, remain_amount) " +
          "VALUE (#{productNo},#{instructionNo},#{amount}, #{amount})")
  void insertProductInstruction(AddProductInstructionDto dto);

  @Update("UPDATE project.product_instruction " +
          "SET amount = #{amount}," +
          "remain_amount = #{amount} - amount + #{amount} "
         +"WHERE product_no = #{productNo} " +
          "AND instruction_no = #{instructionNo} " +
          "AND amount = remain_amount")
  long updateProductInstructionByDto(UpdateProductInstructionDto dto);

  @Delete("DELETE FROM project.product_instruction " +
          "WHERE product_no = #{productNo} AND instruction_no = #{instructionNo}")
  long deleteProductInstructionByDto(DeleteProductInstructionDto dto);

  @Select("SELECT p.product_no as productNo," +
          "p.product_code as productCode," +
          "p.product_name as productName," +
          "pi.amount as amount," +
          "pi.remain_amount as remainAmount " +
          "FROM project.product_instruction pi " +
          "INNER JOIN project.product p ON p.product_no = pi.product_no " +
          "WHERE instruction_no = #{instructionNo}")
  List<ResponseProductInstructionDto> findProductInstructionByInstructionNo(String instructionNo);

  @Select("SELECT remain_amount " +
          "FROM project.product_instruction " +
          "WHERE instruction_no = #{instructionNo} " +
          "AND product_no = #{productNo}")
  ResponseProductRemainAmountDto getProductRemainAmount(String instructionNo, Integer productNo);
}
