package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum ExtraServices {

    EXTENDED_HOURS("Extended hours access"),
    FORKLIFTING("Forklifting"),
    PARCEL_COURIER("Parcel courier service"),
    ACCEPTING_DELIVERIES("Big Green accepting your deliveries"),
    FLEXI_OFFICES("Flexi offices");

    private final String type;

    ExtraServices(String type) {
        this.type = type;
    }
}
