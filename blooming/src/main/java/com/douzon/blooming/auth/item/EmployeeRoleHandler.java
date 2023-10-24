package com.douzon.blooming.auth.item;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    ps.setString(i, parameter.toString());
  }

  @Override
  public EmployeeRole getResult(ResultSet rs, String columnName) throws SQLException {
    String code = rs.getString(columnName);
    return getCodeEnum(code);
  }

  @Override
  public EmployeeRole getResult(ResultSet rs, int columnIndex) throws SQLException {
    String code = rs.getString(columnIndex);
    return getCodeEnum(code);
  }

  @Override
  public EmployeeRole getResult(CallableStatement cs, int columnIndex) throws SQLException {
    String code = cs.getString(columnIndex);
    return getCodeEnum(code);
  }

  private EmployeeRole getCodeEnum(String code) {
    return code.equals("0") ? EmployeeRole.ROLE_ADMIN : EmployeeRole.ROLE_MEMBER;
  }
}
