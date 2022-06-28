package ru.liga;

import java.time.LocalDate;

public class Curs {
    final Integer nominal;
    final LocalDate localDate;
    private final Double curs;
    private final String cdx;

    public Curs(Integer nominal, LocalDate localDate, Double curs, String cdx) {
        this.nominal = nominal;
        this.localDate = localDate;
        this.curs = curs;
        this.cdx = cdx;
    }
    public LocalDate getLocalDate() {
        return localDate;
    }
    public Double geCurs() {
        return curs;
    }

    public String getCdx() {
        return cdx;
    }
}
