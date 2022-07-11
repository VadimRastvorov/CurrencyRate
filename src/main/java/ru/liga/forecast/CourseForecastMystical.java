package ru.liga.forecast;

import ru.liga.ActualCurs;
import ru.liga.Curs;
import ru.liga.Period;
import ru.liga.file.ParseCSV;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CourseForecastMystical implements CourseForecast {

    public List<Curs> getCourseForecastPeriod(String currency, Integer dayCount) {
        List<Curs> courseForecast = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        List<Curs> actualListCurs = getActualCurs(currency, localDate).subList(0, Period.WEEK.dayCount);
        for (int i = 0; i < dayCount; i++) {
            localDate = localDate.plusDays(1);
            Curs curs = getRandomCurs(actualListCurs).withLocalDate(localDate);
            courseForecast.add(curs);
            actualListCurs.add(0, curs);
            actualListCurs.remove(actualListCurs.size() - 1);
        }
        return courseForecast;
    }

    public List<Curs> getCourseForecastDay(String currency, LocalDate date) {
        return getActualCurs(currency, date).subList(0, 1);
    }

    private List<Curs> getActualCurs(String currency, LocalDate date) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        List<Curs> actualListCurs = actualCurs.actualCursList()
                .stream()
                .sorted(Comparator.comparing(Curs::getLocalDate).reversed())
                .collect(Collectors.toList());
        if (actualListCurs.stream().anyMatch(a -> a.getLocalDate().isEqual(date))) {
            return actualListCurs;
        }
        while (!actualListCurs.get(0).getLocalDate().isEqual(date)) {
            LocalDate day = actualListCurs.get(0).getLocalDate().plusDays(1);
            actualListCurs.add(0, getRandomCurs(actualListCurs.subList(0, Period.MONTH.dayCount)).withLocalDate(day));
        }
        return actualListCurs;
    }

    private Curs getRandomCurs(List<Curs> cursList) {
        Random rand = new Random();
        return cursList.get(rand.nextInt(cursList.size()));
    }
}
