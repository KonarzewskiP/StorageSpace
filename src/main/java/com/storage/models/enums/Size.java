package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum Size {
    TELEPHONE_BOX("Telephone box",10, 2.4),
    LARGE_WALK_IN_WARDROBE("Large walk in wardrobe",20, 3.7),
    GARDEN_SHED("Garden shed",30, 5.7),
    LARGE_GARDEN_SHED("Large garden shed",40, 6.09),
    LUTON_VAN("Luton van",75, 8.15),
    LARGE_SINGLE_GARAGE("Large Single Garage",125, 12.69),
    ONE_AND_HALF_GARAGES("1.5 Garages",150, 15.26),
    STANDARD_DOUBLE_GARAGE("Standard double garage",200, 12.13),
    LARGE_DOUBLE_GARAGE("Large double garage",250, 22.43),
    THREE_SINGLE_GARAGES("3 single garage",300, 26.58),
    TWO_DOUBLE_GARAGES("Two double garage",400, 35.15),
    TWO_SHIPPING_CONTAINERS("Two shipping containers",500, 38.32);

    private final String type;
    private final int size;
    private final double price;

    Size(String type, int size, double price) {
        this.type = type;
        this.size = size;
        this.price = price;
    }
}
