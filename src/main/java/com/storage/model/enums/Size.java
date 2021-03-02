package com.storage.model.enums;

import lombok.Getter;

@Getter
public enum Size {
    TELEPHONE_BOX(10, 2.4),
    LARGE_WALK_IN_WARDROBE(20, 3.7),
    GARDEN_SHED(30, 5.7),
    LARGE_GARDEN_SHED(40, 6.09),
    LUTON_VAN(75, 8.15),
    LARGE_SINGLE_GARAGE(125, 12.69),
    ONE_AND_HALF_GARAGES(150, 15.26),
    STANDARD_DOUBLE_GARAGE(200, 12.13),
    LARGE_DOUBLE_GARAGE(250, 22.43),
    THREE_SINGLE_GARAGES(300, 26.58),
    TWO_DOUBLE_GARAGES(400, 35.15),
    TWO_SHIPPING_CONTAINERS(500, 38.32);

    private int size;
    private double price;

    Size(int size, double price) {
        this.size = size;
        this.price = price;
    }
}
