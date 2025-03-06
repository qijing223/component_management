package com.lot.server.component.mapper;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.lot.server.component.domain.entity.ComponentStatus;

import java.sql.*;

public class ComponentStatusTypeHandler extends BaseTypeHandler<ComponentStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ComponentStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public ComponentStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ComponentStatus.fromValue(rs.getString(columnName));
    }

    @Override
    public ComponentStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ComponentStatus.fromValue(rs.getString(columnIndex));
    }

    @Override
    public ComponentStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ComponentStatus.fromValue(cs.getString(columnIndex));
    }
}

