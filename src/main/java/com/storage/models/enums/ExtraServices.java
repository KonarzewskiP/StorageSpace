package com.storage.models.enums;

public enum ExtraServices {

    EXTENDED_HOURS("Extended hours access"),
    FORKLIFTING("Forklifting"),
    PARCEL_COURIER("Parcel courier service"),
    ACCEPTING_DELIVERIES("Big Green accepting your deliveries"),
    FLEXI_OFFICES("Flexi offices");

    private String type;

    ExtraServices(String type) {
        this.type = type;
    }
}
