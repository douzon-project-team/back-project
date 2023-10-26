package com.douzon.blooming.product_instruction.repo;

import com.douzon.blooming.instruction.dto.ProductListDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ProductInstructionRepository {

    @Insert("${insertQuery}")
    void insert(String insertQuery);


    @Select("SELECT product_no, amount FROM product_instruction WHERE instruction_no = #{instructionNo}")
    List<ProductListDto> getProductList(String instructionNo);
}
