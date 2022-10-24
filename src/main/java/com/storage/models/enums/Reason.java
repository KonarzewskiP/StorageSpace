package com.storage.models.enums;

public enum Reason {

    MOVING,
    STORAGE,
    IMPROVEMENTS,
    PERSONAL_CHANGE,
    TRAVELLING,
    OTHER;

    @Override
    public String toString() {
      return switch (this) {
            case MOVING ->  "Moving";
            case STORAGE ->  "Decluttering";
            case TRAVELLING ->  "Travelling";
            case IMPROVEMENTS -> "Home improvements";
            case PERSONAL_CHANGE ->  "Change in personal circumstances";
            default ->  "Other";
        };
    }

}
