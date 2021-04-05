package com.storage.models.enums;

public enum Reason {

    MOVING,
    DECLUTTERING,
    IMPROVEMENTS,
    PERSONAL_CHANGE,
    TRAVELLING,
    OTHER;

    @Override
    public String toString() {

        switch (this) {
            case MOVING -> {
                return "Moving";
            }
            case DECLUTTERING -> {
                return "Decluttering";
            }
            case TRAVELLING -> {
                return "Travelling";
            }
            case IMPROVEMENTS -> {
                return "Home improvements";
            }
            case PERSONAL_CHANGE -> {
                return "Change in personal circumstances";
            }
            default ->  {
                return "Other";
            }
        }
    }
}
