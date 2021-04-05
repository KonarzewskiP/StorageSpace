package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum ExtraServices {

    EXTENDED_HOURS,
    FORKLIFTING,
    PARCEL_COURIER,
    ACCEPTING_DELIVERIES,
    FLEXI_OFFICES;


    @Override
    public String toString() {

        switch (this) {
            case FORKLIFTING -> {
                return "Forklifting";
            }
            case FLEXI_OFFICES -> {
                return "Flexi offices";
            }
            case EXTENDED_HOURS -> {
                return "Extended hours access";
            }
            case PARCEL_COURIER -> {
                return "Parcel courier service";
            }
            case ACCEPTING_DELIVERIES -> {
                return "Accepting your deliveries";
            }
        }
        return "Unknown service";
    }
}
