package com.lot.server.component.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComponentStatus {
    AVAILABLE("available"),
    BORROW_OUT("borrow-out");

    private final String value;

    ComponentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static ComponentStatus fromValue(String value) {
        for (ComponentStatus status : ComponentStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
