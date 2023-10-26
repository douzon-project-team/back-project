package com.douzon.blooming.customer.repo;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerRepository {

    @Select("SELECT customder_code FROM customer WHERE customer_code = #{customerCoode}")
    String customerCodeCheck(String customerCode);

    @Insert("INSERT INTO customer(customer_code, customer_name, customer_tel) VALUES (#{customerCode}, #{customerNme}, #{customerTel}")
    void insertCustomer(RequestCustomerDto dto);

    @Select("SELECT customer_no FROM customer WHERE customer_name = #{customerName}")
    Long findCustomerNoByName(String customerName);

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

    @Update("UPDATE employee SET customer_name = #{dto.customerName}, custoemr_tel = #{dto.customerTel} WHERE customer_no = #{customerNo}")
    void updateCustomer(@Param("dto")UpdateCustomerDto dto, Long customerNo);

    @Delete("DELETE FROM customer WHERE customer_no = #{customerCode}")
    void deleteCustomer(Long customerNo);
}
