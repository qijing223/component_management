package com.lot.server.activity.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityAction {
    STOCK_IN("Stock-in"),
    STOCK_OUT("Stock-out"),
    BORROW("Borrow"),
    RETURN("Return"),
    DISPOSE("Dispose");

    private final String value;

    ActivityAction(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static ActivityAction fromValue(String value) {
        for (ActivityAction action : ActivityAction.values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknown action: " + value);
    }
}
