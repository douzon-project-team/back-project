package com.douzon.blooming.product.repo;

import com.douzon.blooming.product.dto.request.RequestProductDto;
import com.douzon.blooming.product.dto.response.ListProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductRepository {

  @Insert(value = "INSERT INTO project.product (product_code, designation, standard, unit) VALUE (#{productCode},#{designation},#{standard},#{unit})")
  void insertByRequestProductDto(RequestProductDto requestProductDto);

  @Delete("DELETE FROM project.product WHERE product_no = #{productNo}")
  void deleteByProductNo(long productNo);

  @Update("UPDATE project.product SET product_code = #{productCode}, designation = #{designation} ,standard = #{standard}, unit = #{unit} WHERE product_no = #{productNo}")
  void updateByRequestProductDto(RequestProductDto requestProductDto);

  @Select("SELECT * FROM project.product WHERE product_code = #{productCode}")
  Optional<ProductDto> findByProductCode(String productCode);

  @Select("SELECT * FROM project.product WHERE product_code LIKE #{productCode}")
  List<ListProductDto> findAllByProductCodeLike(String productCode);

  @Select("SELECT * FROM project.product WHERE product_code LIKE #{designation}")
  List<ListProductDto> findAllByDesignationLike(String designation);

}
