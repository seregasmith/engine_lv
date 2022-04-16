package ru.smith.engine_lv.api.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleType {
    CAR("CAR"),
    VAN("VAN");

    private String type;

    VehicleType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static VehicleType fromString(String type) {
        return type == null
                ? null
                : VehicleType.valueOf(type.toUpperCase());
    }

    @JsonValue
    public String getPhoneType() {
        return this.type.toUpperCase();
    }
}
