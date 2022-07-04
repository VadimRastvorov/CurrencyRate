package ru.liga;

public enum Period {
    DAY(1),
    WEEK(7),
    MONTH(30);
    public Integer period;

    Period(Integer period) {
        this.period = period;
    }

    Period() {
    }
}
