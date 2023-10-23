package com.douzon.blooming.customer.repo;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerRepository {

    @Insert("INSERT INTO customer(code, name, tel) VALUES (#{customerCode}, #{customerNme}, #{customerTel}")
    void insertCustomer(RequestCustomerDto dto);

    @Select("SELECT * FROM customer WHERER customer_no = #{customerNo}")
    ResponseCustomerDto getCustomer(Long customerNo);

    @Select("<script>" +
            "SELECT COUNT(*) FROM employee" +
            "<where>" +
                "<if test='customerName != null'>" +
                    "name LIKE CONCAT('%', #{customerName}, '%')" +
                "</if>" +
            "</where>" +
            "</script>")
    Integer getCountCustomers(String customerName);

    @Select("<script>" +
            "SELECT COUNT(*) FROM employee" +
            "<where>" +
                "<if test='customerName != null'>" +
                    "name LIKE CONCAT('%', #{customerName}, '%')" +
                "</if>" +
            "</where>" +
            "LIMIT #{start}, #{pageSize}" +
            "</script>")
    List<ResponseCustomerDto> getCustomerList(String customerName, int start, int pageSize);

    @Update("UPDATE employee SET name = #{dto.customerName}, tel = #{dto.customerTel} WHERE customer_no = #{customerNo}")
    void updateCustomer(@Param("dto")UpdateCustomerDto dto, Long customerNo);

    @Delete("DELETE FROM customer WHERE customer_no = #{customerCode}")
    void deleteCustomer(Long customerNo);
}
