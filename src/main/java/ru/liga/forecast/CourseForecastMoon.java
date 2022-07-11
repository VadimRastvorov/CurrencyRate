package ru.liga.forecast;

import ru.liga.ActualCurs;
import ru.liga.Curs;
import ru.liga.file.ParseCSV;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CourseForecastMoon implements CourseForecast{
    public List<Curs> getCourseForecastPeriod(String currency, Integer period) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        return actualCurs.actualCursList()
                .stream()
                .sorted(Comparator.comparing(Curs::getLocalDate))
                .filter(c -> c.getLocalDate().isBefore(LocalDate.now().minusYears(1).plusDays(period)) &&
                        c.getLocalDate().isAfter(LocalDate.now().minusYears(1).minusDays(1)))
                .collect(Collectors.toList());
    }

    public List<Curs> getCourseForecastDay(String currency, LocalDate date) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        LocalDate inDate = date;
        LocalDate actualDate = actualCurs.actualCursList()
                .stream()
                .map(Curs::getLocalDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());
        while (inDate.isAfter(actualDate)) {
            inDate = inDate.minusYears(1);
        }
        LocalDate finalInDate = inDate;

        return actualCurs.actualCursList()
                .stream()
                .sorted(Comparator.comparing(Curs::getLocalDate))
                .filter(c -> c.getLocalDate().isEqual(finalInDate.minusYears(1)))
                .collect(Collectors.toList());
    }
}
