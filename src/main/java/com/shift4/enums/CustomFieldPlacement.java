package com.shift4.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CustomFieldPlacement {
    STANDARD("standard"),
    CUSTOMER("customer"),

    /**
     * Used when received value can't be mapped to this enumeration.
     */
    UNRECOGNIZED("unrecognized"),
    ;

    private final String value;

    CustomFieldPlacement(String value) {
        this.value = value;
    }

    @JsonCreator
    public static CustomFieldPlacement fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (CustomFieldPlacement placement : values()) {
            if (placement.value.equalsIgnoreCase(value)) {
                return placement;
            }
        }

        return UNRECOGNIZED;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
