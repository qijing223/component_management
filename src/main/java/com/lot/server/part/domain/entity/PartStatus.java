package com.lot.server.part.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PartStatus {
    AVAILABLE("available"),
    BORROW_OUT("borrow-out"),
    UNAVAILABLE("unavailable");

    private final String value;

    PartStatus(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static PartStatus fromValue(String value) {
        for (PartStatus status : PartStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }

    public String getValue() {
        return this.value;
    }

}
