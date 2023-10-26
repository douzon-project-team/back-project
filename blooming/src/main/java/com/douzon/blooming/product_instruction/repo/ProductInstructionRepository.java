package com.douzon.blooming.product_instruction.repo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductInstructionRepository {

    @Insert("${insertQuery}")
    void insert(String insertQuery);
}
