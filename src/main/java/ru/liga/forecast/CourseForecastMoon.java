package ru.liga.forecast;

import ru.liga.ActualCurs;
import ru.liga.Curs;
import ru.liga.file.ParseCSV;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CourseForecastMoon {
    public List<Curs> getActualCursAlgMoon(String currency, Integer period) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        return actualCurs.actualCursList()
                .stream()
                .sorted((c1, c2) -> c1.getLocalDate().compareTo(c2.getLocalDate()))
                .filter(c -> c.getLocalDate().isBefore(LocalDate.now().minusYears(1).plusDays(period)) &&
                        c.getLocalDate().isAfter(LocalDate.now().minusYears(1)))
                .collect(Collectors.toList());
    }
}
