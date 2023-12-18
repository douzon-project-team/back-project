package com.douzon.blooming.log;

import com.douzon.blooming.log.exception.UnsupportedLogTypeException;
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
@MappedTypes(LogType.class)
public class LogTypeHandler implements TypeHandler<LogType> {

  @Override
  public void setParameter(PreparedStatement ps, int i, LogType parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setInt(i, parameter.ordinal());
  }

  @Override
  public LogType getResult(ResultSet rs, String columnName) throws SQLException {
    int code = rs.getInt(columnName);
    return getCodeEnum(code);
  }

  @Override
  public LogType getResult(ResultSet rs, int columnIndex) throws SQLException {
    int code = rs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  @Override
  public LogType getResult(CallableStatement cs, int columnIndex) throws SQLException {
    int code = cs.getInt(columnIndex);
    return getCodeEnum(code);
  }

  private LogType getCodeEnum(int code) {
    return Arrays.stream(LogType.values())
        .filter(type -> type.ordinal() == code)
        .findAny()
        .orElseThrow(UnsupportedLogTypeException::new);
  }
}
