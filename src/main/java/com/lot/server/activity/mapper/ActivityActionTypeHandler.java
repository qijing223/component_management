package com.lot.server.activity.mapper;

import com.lot.server.activity.domain.entity.ActivityAction;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 * MyBatis TypeHandler for converting ActivityAction enum to and from database.
 */
@MappedTypes(ActivityAction.class)
public class ActivityActionTypeHandler extends BaseTypeHandler<ActivityAction> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ActivityAction parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public ActivityAction getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : ActivityAction.fromValue(value);
    }

    @Override
    public ActivityAction getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : ActivityAction.fromValue(value);
    }

    @Override
    public ActivityAction getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : ActivityAction.fromValue(value);
    }
}
