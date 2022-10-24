package com.storage.models.enums;

import lombok.Getter;

@Getter
public enum StorageDuration {
    UP_TO_2_WEEKS("Less than 2 weeks"),
    UP_TO_4_WEEKS("2-4 weeks"),
    UP_TO_8_WEEKS("4-8 weeks"),
    UP_TO_6_MONTHS("2-6 months"),
    UP_TO_1_YEAR("6-12 months"),
    PLUS_1_YEAR("1 year +");

    private final String duration;

    StorageDuration(String duration) {
        this.duration = duration;
    }
}
