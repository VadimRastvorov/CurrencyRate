package ru.liga;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rate {
    public Integer lastDays = 7;

    public List<Curs> getRate(String currency, String period) {
        if (period.equals("tomorrow")) {
            return getRateTomorrow(currency);
        } else if (period.equals("week")) {
            return getRateWeek(currency);
        }
        return null;
    }

    private List<Curs> getRateTomorrow(String currency) {
        List<Curs> rateTomorrow = new ArrayList<>();
        ParseCSV parse = new ParseCSV();
        rateTomorrow.add(new Curs(0, addLocalDate(LocalDate.now(), 1), getCurs(parse.getCurs(currency).subList(0, lastDays)), currency));
        return rateTomorrow;
    }

    private List<Curs> getRateWeek(String currency) {
        List<Curs> rateTomorrow = new ArrayList<>();
        ParseCSV parse = new ParseCSV();
        LocalDate localDate = LocalDate.now();
        List<Curs> curs = parse.getCurs(currency).subList(0, lastDays);
        for (int i = 0; i < 7; i++) {
            localDate = addLocalDate(localDate);
            Curs cur = new Curs(0, localDate, getCurs(curs), currency);
            rateTomorrow.add(cur);
            curs.add(0, cur);
            curs.remove(curs.size() - 1);
        }
        return rateTomorrow;
    }

    private Double getCurs(List<Curs> curs) {
        double cursMath = 0;
        for (Curs cur : curs) {
            cursMath += cur.geCurs();
        }
        return cursMath / curs.size();
    }

    private LocalDate addLocalDate(LocalDate date, int... days) {
        return date.plusDays(days.length > 0 ? days[0] : 1);
    }
}
