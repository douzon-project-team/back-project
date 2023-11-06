package com.douzon.blooming.delivery;

import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
@MappedTypes(DeliveryStatus.class)
public class DeliveryStatusTypeHandler implements TypeHandler<DeliveryStatus> {

    @Override
    public void setParameter(PreparedStatement ps, int i, DeliveryStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public DeliveryStatus getResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return getCodeEnum(code);
    }

    @Override
    public DeliveryStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return getCodeEnum(code);
    }

    @Override
    public DeliveryStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return getCodeEnum(code);
    }

    private DeliveryStatus getCodeEnum(int code) {
        return Arrays.stream(DeliveryStatus.values()).filter(role -> role.ordinal() == code)
                .findAny()
                .orElseThrow(UnsupportedEmployeeRoleException::new);
    }
}

