package ru.liga;

import java.time.LocalDate;

public class Curs {
    public Integer nominal;
    public LocalDate date;
    public Double curs;
    public String cdx;

    public Curs(Integer nominal, LocalDate date, Double curs, String cdx) {
        this.nominal = nominal;
        this.date = date;
        this.curs = curs;
        this.cdx = cdx;
    }
}
