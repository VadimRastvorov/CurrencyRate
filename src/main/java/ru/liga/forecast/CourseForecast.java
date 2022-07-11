package ru.liga.forecast;

import ru.liga.Curs;

import java.time.LocalDate;
import java.util.List;

public interface CourseForecast {
    List<Curs> getCourseForecastPeriod(String currency, Integer period);

    List<Curs> getCourseForecastDay(String currency, LocalDate date);


}
