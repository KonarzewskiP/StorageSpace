package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum ExtraServices {
    EXTENDED_HOURS,
    FORKLIFTING,
    PARCEL_COURIER,
    ACCEPTING_DELIVERIES;

    @Override
    public String toString() {
        return switch (this) {
            case FORKLIFTING -> "Forklifting";
            case EXTENDED_HOURS -> "Extended hours access";
            case PARCEL_COURIER -> "Parcel courier service";
            case ACCEPTING_DELIVERIES -> "Accepting your deliveries";
        };
    }
}
