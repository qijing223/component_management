package com.lot.server.activity.mapper;

import com.lot.server.activity.domain.entity.ActivityAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ActivityActionTypeHandler
 */
@Disabled
@SpringBootTest
public class ActivityActionTypeHandlerTest {

    private ActivityActionTypeHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ActivityActionTypeHandler();
    }

    @Test
    void testSetNonNullParameter() throws SQLException {
        // Arrange
        PreparedStatement ps = mock(PreparedStatement.class);
        ActivityAction action = ActivityAction.STOCK_IN;

        // Act
        handler.setNonNullParameter(ps, 1, action, null);

        // Assert
        verify(ps).setString(1, "Stock-in");
    }

    @Test
    void testGetNullableResult_ResultSet_String() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("action")).thenReturn("Stock-in");

        // Act
        ActivityAction result = handler.getNullableResult(rs, "action");

        // Assert
        assertEquals(ActivityAction.STOCK_IN, result);
    }

    @Test
    void testGetNullableResult_ResultSet_Int() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString(1)).thenReturn("Borrow");

        // Act
        ActivityAction result = handler.getNullableResult(rs, 1);

        // Assert
        assertEquals(ActivityAction.BORROW, result);
    }

    @Test
    void testGetNullableResult_CallableStatement() throws SQLException {
        // Arrange
        CallableStatement cs = mock(CallableStatement.class);
        when(cs.getString(1)).thenReturn("Return");

        // Act
        ActivityAction result = handler.getNullableResult(cs, 1);

        // Assert
        assertEquals(ActivityAction.RETURN, result);
    }

    @Test
    void testGetNullableResult_Null() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("action")).thenReturn(null);

        // Act
        ActivityAction result = handler.getNullableResult(rs, "action");

        // Assert
        assertNull(result);
    }

    @Test
    void testGetNullableResult_InvalidAction() throws SQLException {
        // Arrange
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("action")).thenReturn("InvalidAction");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            handler.getNullableResult(rs, "action");
        });
    }
} 