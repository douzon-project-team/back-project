package com.douzon.blooming.productlog.item;

import com.douzon.blooming.productlog.exception.UnsupportedProductLogTypeException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

@Component
@MappedTypes(ProductLogType.class)
public class ProductLogTypeHandler implements TypeHandler<ProductLogType> {

  @Override
  public void setParameter(PreparedStatement ps, int i, ProductLogType parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setInt(i, parameter.ordinal());
  }

  @Override
  public ProductLogType getResult(ResultSet rs, String columnName) throws SQLException {
    int code = rs.getInt(columnName);
    return getCodeEnum(code);
  }

  @Override
  public ProductLogType getResult(ResultSet rs, int columnIndex) throws SQLException {
    int code = rs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  @Override
  public ProductLogType getResult(CallableStatement cs, int columnIndex) throws SQLException {
    int code = cs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  private ProductLogType getCodeEnum(int code) {
    return Arrays.stream(ProductLogType.values())
        .filter(type -> type.ordinal() == code)
        .findAny()
        .orElseThrow(UnsupportedProductLogTypeException::new);
  }
}
