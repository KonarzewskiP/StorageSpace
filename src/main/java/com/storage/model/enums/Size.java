package com.storage.model.enums;

import lombok.Getter;

@Getter
public enum Size {
    TELEPHONE_BOX(10),
    LARGE_WALK_IN_WARDROBE(20),
    GARDEN_SHED(30),
    LARGE_GARDEN_SHED(40),
    LUTON_VAN(75),
    LARGE_SINGLE_GARAGE(125),
    ONE_AND_HALF_GARAGES(150),
    STANDARD_DOUBLE_GARAGE(200),
    LARGE_DOUBLE_GARAGE(250),
    THREE_SINGLE_GARAGES(300),
    TWO_DOUBLE_GARAGES(400),
    TWO_SHIPPING_CONTAINERS(500);

    private int size;

    Size(int size) {
        this.size = size;
    }
}
