package com.lot.server.activity.mapper;

import com.lot.server.activity.domain.entity.ActivityAction;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * MyBatis TypeHandler for converting ActivityAction enum to and from database.
 */
@MappedTypes(ActivityAction.class)
public class ActivityActionTypeHandler extends BaseTypeHandler<ActivityAction> {
    private static final Logger logger = LoggerFactory.getLogger(ActivityActionTypeHandler.class);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ActivityAction parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public ActivityAction getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        logger.debug("Converting value from database: {}", value);
        return value == null ? null : convertToEnum(value);
    }

    @Override
    public ActivityAction getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        logger.debug("Converting value from database: {}", value);
        return value == null ? null : convertToEnum(value);
    }

    @Override
    public ActivityAction getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        logger.debug("Converting value from database: {}", value);
        return value == null ? null : convertToEnum(value);
    }

    private ActivityAction convertToEnum(String value) {
        logger.debug("Attempting to convert value: {}", value);
        String normalizedValue = value.toUpperCase().replace("-", "_");
        try {
            return ActivityAction.valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            logger.debug("Direct conversion failed, trying alternative formats");
            switch (value.toLowerCase()) {
                case "stock-in":
                    return ActivityAction.STOCK_IN;
                case "stock-out":
                    return ActivityAction.STOCK_OUT;
                case "borrow":
                    return ActivityAction.BORROW;
                case "return":
                    return ActivityAction.RETURN;
                case "dispose":
                    return ActivityAction.DISPOSE;
                default:
                    logger.error("Failed to convert value: {}", value);
                    throw new IllegalArgumentException("Unknown action: " + value);
            }
        }
    }
}
