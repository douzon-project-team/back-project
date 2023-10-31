package com.douzon.blooming.auth;

import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
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
@MappedTypes(EmployeeRole.class)
public class EmployeeRoleHandler implements TypeHandler<EmployeeRole> {

  @Override
  public void setParameter(PreparedStatement ps, int i, EmployeeRole parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setInt(i, parameter.ordinal());
  }

  @Override
  public EmployeeRole getResult(ResultSet rs, String columnName) throws SQLException {
    int code = rs.getInt(columnName);
    return getCodeEnum(code);
  }

  @Override
  public EmployeeRole getResult(ResultSet rs, int columnIndex) throws SQLException {
    int code = rs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  @Override
  public EmployeeRole getResult(CallableStatement cs, int columnIndex) throws SQLException {
    int code = cs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  private EmployeeRole getCodeEnum(int code) {
    return Arrays.stream(EmployeeRole.values()).filter(role -> role.ordinal() == code)
        .findAny()
        .orElseThrow(UnsupportedEmployeeRoleException::new);
  }
}
