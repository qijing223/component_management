package com.lot.server.activity.domain.entity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ActivityAction enum
 */
@Disabled
@SpringBootTest
public class ActivityActionTest {

    @Test
    void testToString() {
        assertEquals("Stock-in", ActivityAction.STOCK_IN.toString());
        assertEquals("Stock-out", ActivityAction.STOCK_OUT.toString());
        assertEquals("Borrow", ActivityAction.BORROW.toString());
        assertEquals("Return", ActivityAction.RETURN.toString());
        assertEquals("Dispose", ActivityAction.DISPOSE.toString());
    }

    @Test
    void testFromValue_ValidValues() {
        assertEquals(ActivityAction.STOCK_IN, ActivityAction.fromValue("Stock-in"));
        assertEquals(ActivityAction.STOCK_OUT, ActivityAction.fromValue("Stock-out"));
        assertEquals(ActivityAction.BORROW, ActivityAction.fromValue("Borrow"));
        assertEquals(ActivityAction.RETURN, ActivityAction.fromValue("Return"));
        assertEquals(ActivityAction.DISPOSE, ActivityAction.fromValue("Dispose"));
    }

    @Test
    void testFromValue_CaseInsensitive() {
        assertEquals(ActivityAction.STOCK_IN, ActivityAction.fromValue("stock-in"));
        assertEquals(ActivityAction.STOCK_OUT, ActivityAction.fromValue("STOCK-OUT"));
        assertEquals(ActivityAction.BORROW, ActivityAction.fromValue("borrow"));
        assertEquals(ActivityAction.RETURN, ActivityAction.fromValue("RETURN"));
        assertEquals(ActivityAction.DISPOSE, ActivityAction.fromValue("dispose"));
    }

    @Test
    void testFromValue_InvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            ActivityAction.fromValue("InvalidAction");
        });
    }
} 