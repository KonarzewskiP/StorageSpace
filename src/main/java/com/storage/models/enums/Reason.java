package com.storage.models.enums;

public enum Reason {

    MOVING("Moving"),
    DECLUTTERING("Decluttering"),
    IMPROVEMENTS("Home improvements"),
    PERSONAL_CHANGE("Change in personal circumstances"),
    TRAVELLING("Travelling"),
    OTHER("Other");

    private String reason;

    Reason(String reason) {
        this.reason = reason;
    }
}
