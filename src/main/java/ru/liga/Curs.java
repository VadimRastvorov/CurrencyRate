package ru.liga;

import java.util.Date;

public class Curs {
    public Integer nominal;
    public Date date;
    public Double curs;
    public String cdx;

    public Curs(Integer nominal, Date date, Double curs, String cdx) {
        this.nominal = nominal;
        this.date = date;
        this.curs = curs;
        this.cdx = cdx;
    }
}
