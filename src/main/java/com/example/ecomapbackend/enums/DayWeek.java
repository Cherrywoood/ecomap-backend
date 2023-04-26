package com.example.ecomapbackend.enums;

public enum DayWeek {
    MON("Пн"),
    TUE("Вт"),
    WED("Ср"),
    THU("Чт"),
    FRI("Пт"),
    SAT("Сб"),
    SUN("Вс");

    private final String value;

    DayWeek(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
