package ru.liga;

import java.util.HashMap;
import java.util.Map;

public enum Period {
    DAY("tomorrow", 1),
    WEEK("week", 7),
    MONTH("month", 30),
    YEAR("year", 365);

    private static final Map<Object, Object> PERIOD = new HashMap<>();
    private static final Map<Object, Object> DAY_COUNT = new HashMap<>();

    static {
        for (Period e : values()) {
            PERIOD.put(e.period, e);
            DAY_COUNT.put(e.dayCount, e);
        }
    }
    public final String period;
    public final Integer dayCount;

    Period(String label, int dayCount) {
        this.period = label;
        this.dayCount = dayCount;
    }

    public static Period valueOfPeriod(String period) {
        return (Period) PERIOD.get(period);
    }

    public static Period valueOfDayCount(Integer dayCount) {
        return (Period) DAY_COUNT.get(dayCount);
    }
}
