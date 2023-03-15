package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum StorageSize {
    TELEPHONE_BOX("Telephone box", 1),
//    LARGE_WALK_IN_WARDROBE("Large walk in wardrobe", 2),
//    GARDEN_SHED("Garden shed", 3),
    LARGE_GARDEN_SHED("Large garden shed", 4),
//    LUTON_VAN("Luton van", 7),
//    LARGE_SINGLE_GARAGE("Large Single Garage", 11.5f),
    ONE_AND_HALF_GARAGES("1.5 Garages", 14),
//    STANDARD_DOUBLE_GARAGE("Standard double garage", 18.5f),
//    LARGE_DOUBLE_GARAGE("Large double garage", 23.5f),
    THREE_SINGLE_GARAGES("3 single garage", 28);
//    TWO_DOUBLE_GARAGES("Two double garage", 37.5f),
//    TWO_SHIPPING_CONTAINERS("Two shipping containers", 46.5f);

    private final String description;
    private final float sizeInSqMeters;

    StorageSize(String description, float sizeInSqMeters) {
        this.description = description;
        this.sizeInSqMeters = sizeInSqMeters;
    }
}
