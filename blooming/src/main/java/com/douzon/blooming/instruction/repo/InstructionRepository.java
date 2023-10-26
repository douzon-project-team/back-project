package com.douzon.blooming.instruction.repo;

import com.douzon.blooming.instruction.dto.InsertDto;
import com.douzon.blooming.instruction.dto.ProductListDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionListDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface InstructionRepository {

    @Insert("INSERT INTO instruction(instruction_date, customer_no, employee_no, progress_status, expiration_date) " +
            "VALUES (#{dto.instructionDate}, #{dto.customerNo}, #{dto.employeeNo}, #{dto.progressStatus}, #{dto.expirationDate})")
    void insertInstruction(@Param("dto") InsertDto dto);

    @Select("SELECT instruction_no FROM instruction ORDER BY SUBSTRING(instruction_no,4,4) DESC,SUBSTRING(instruction_no,7) DESC")
    String getInstructionNo();

    // 방법 1 . 지시테이블에서 List를 제외한 나머지를 받아오고 품목_지시 테이블에서 품목 List를 가져와서 setter 해서 하나의 객체를 만들어서 프론트로 보낸다.
    @Select("SELECT I.instruction_no as instruction_no, I.instruction_date as instruction_date, C.customer_name as customer_name, E.name as employee_name, " +
            "I.progress_status as progress_status, I.expiration_date as expiration_date " +
            "FROM instruction I INNER JOIN customer C ON I.customer_no = C.customer_no " +
            "INNER JOIN employee E ON I.employee_no = E.employee_no " +
            "WHERE I.instruction_no = #{instructionNo}")
    Optional<ResponseInstructionDto> findByInstructionNo(String instructionNo);

    @Select("SELECT product_no, amount from product_instruction WHERE instruction_no = #{instructionNo}")
    List<ProductListDto> getProductList(String instructionNo);


    Integer getCountInstructions(SearchDto searchDto);

    @Select("SELECT I.instruction_no as instruction_no, I.instruction_date as instruction_date, C.customer_name as customer_name, E.name as employee_name, " +
            "I.progress_status as progress_status, I.expiration_date as expiration_date " +
            "FROM instruction I INNER JOIN customer C ON I.customer_no = C.customer_no " +
            "INNER JOIN employee E ON I.employee_no = E.employee_no " +
            "WHERE I.instruction_no = #{instructionNo}")
    List<ResponseInstructionDto> findInstructionList(SearchDto searchDto, Integer start, Integer end);

    @Update("UPDATE instruction SET ")
    void updateInstruction(String instructionNo, RequestInstructionDto dto);

    @Delete("DELETE FROM instruction WHERE instruction_no = #{instructionNo}")
    Optional<RequestInstructionDto> deleteInstruction(String instructionNo);


}
