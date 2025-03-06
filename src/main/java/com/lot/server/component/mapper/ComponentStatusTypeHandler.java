package com.lot.server.component.mapper;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.lot.server.component.domain.entity.ComponentStatus;

import java.sql.*;

public class ComponentStatusTypeHandler extends BaseTypeHandler<ComponentStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ComponentStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString()); // 存储到数据库时转换为字符串
    }

    @Override
    public ComponentStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ComponentStatus.fromValue(rs.getString(columnName)); // 查询时转换为枚举
    }

    @Override
    public ComponentStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ComponentStatus.fromValue(rs.getString(columnIndex)); // 查询时转换为枚举
    }

    @Override
    public ComponentStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ComponentStatus.fromValue(cs.getString(columnIndex)); // 查询时转换为枚举
    }
}

