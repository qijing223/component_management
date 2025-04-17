package com.lot.server.part.mapper;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.lot.server.part.domain.entity.PartStatus;

import java.sql.*;

public class PartStatusTypeHandler extends BaseTypeHandler<PartStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PartStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public PartStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return PartStatus.fromValue(rs.getString(columnName));
    }

    @Override
    public PartStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return PartStatus.fromValue(rs.getString(columnIndex));
    }

    @Override
    public PartStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return PartStatus.fromValue(cs.getString(columnIndex));
    }
}

