package com.douzon.blooming.instruction;

import com.douzon.blooming.auth.EmployeeRole;
import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
@MappedTypes(ProgressStatus.class)
public class StatusTypeHandler implements TypeHandler<ProgressStatus> {

    @Override
    public void setParameter(PreparedStatement ps, int i, ProgressStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public ProgressStatus getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return getCodeEnum(code);
    }

    @Override
    public ProgressStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return getCodeEnum(code);
    }

    @Override
    public ProgressStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return getCodeEnum(code);
    }

    private ProgressStatus getCodeEnum(int code) {
        return Arrays.stream(ProgressStatus.values()).filter(role -> role.ordinal() == code)
                .findAny()
                .orElseThrow(UnsupportedEmployeeRoleException::new);
    }
}
