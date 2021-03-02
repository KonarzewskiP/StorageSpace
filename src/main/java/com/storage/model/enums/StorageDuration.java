package com.storage.model.enums;

import lombok.Getter;

@Getter
public enum StorageDuration {
    LESS_THAN_2_WEEKS("Less than 2 weeks"),
    TWO_FOUR_WEEKS("2-4 weeks"),
    FOUR_EIGHT_WEEKS("4-8 weeks"),
    EIGHT_TWELVE_WEEKS("8-12 weeks"),
    THREE_SIX_MONTHS("3-6 months"),
    SIX_TWELVE_MOTHS("6-12 months"),
    PLUS_1_YEAR("1 year+");

    private String duration;

    StorageDuration(String duration) {
        this.duration = duration;
    }
}
