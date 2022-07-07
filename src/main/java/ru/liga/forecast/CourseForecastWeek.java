package ru.liga.forecast;

import ru.liga.ActualCurs;
import ru.liga.Curs;
import ru.liga.Period;
import ru.liga.file.ParseCSV;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseForecastWeek {
    private final Integer BIG_DECIMAL_SCALE = 4;
    /**
     * метод прогнозирует курс валюты, на переданные дни
     * алгоритм среднее за последнюю неделю
     *
     * @param currency валюта
     * @param dayCount количество дней
     * @return вернем спрогнозированный период
     */
    public List<Curs> getCourseForecastPeriodAlgWeek(String currency, Integer dayCount) {
        List<Curs> courseForecast = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        List<Curs> actualListCurs = getActualCursAlgWeek(currency, localDate).subList(0, Period.WEEK.period);
        for (int i = 0; i < dayCount; i++) {
            localDate = localDate.plusDays(1);
            Curs curs = new Curs(
                    actualListCurs.get(0).getNominal(),
                    localDate,
                    getAverageCurs(actualListCurs),
                    actualListCurs.get(0).getCdx());
            courseForecast.add(curs);
            actualListCurs.add(0, curs);
            actualListCurs.remove(actualListCurs.size() - 1);
        }
        return courseForecast;
    }

    /**
     * Метод возвращает курс за определюенную дату
     *
     * @param currency валюта
     * @param date     дата за которую вернуть курс
     * @return вернем курс.
     */
    public List<Curs> getCourseForecastDate(String currency, LocalDate date) {
        List<Curs> actualListCurs = getActualCursAlgWeek(currency, date);
        if (actualListCurs
                .stream()
                .anyMatch(a -> a.getLocalDate().isEqual(date))) {
            return actualListCurs.stream()
                    .filter(a -> a.getLocalDate().isEqual(date))
                    .collect(Collectors.toList());
        }
        return actualListCurs.subList(0, 1);
    }

    /**
     * метод актуализирует List<Curs>
     * сначало в распарсенных данных добавляет курсы (пробелы по датам) с предыдущей заполненной датой,
     * потом подсчитывает от последней даты до переданой в параметре date
     * по алгоритму среднее за предыдущие 7 дней
     *
     * @param currency валюта
     * @param date     дата до которой забиваем массив
     * @return возвращаем массив Curs
     */
    private List<Curs> getActualCursAlgWeek(String currency, LocalDate date) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        List<Curs> actualListCurs = actualCurs.actualCursList()
                .stream()
                .sorted((c1, c2) -> c2.getLocalDate().compareTo(c1.getLocalDate()))
                .collect(Collectors.toList());
        if (actualListCurs.stream().anyMatch(a -> a.getLocalDate().isEqual(date))) {
            return actualListCurs;
        }
        while (!actualListCurs.get(0).getLocalDate().isEqual(date)) {
            LocalDate day = actualListCurs.get(0).getLocalDate().plusDays(1);
            Curs cursForDate = new Curs(
                    actualListCurs.get(0).getNominal(),
                    day,
                    getAverageCurs(actualListCurs.subList(0, Period.WEEK.period)),
                    actualListCurs.get(0).getCdx());
            actualListCurs.add(0, cursForDate);
        }
        return actualListCurs;
    }
    private BigDecimal getAverageCurs(List<Curs> cursList) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (Curs curs : cursList) {
            bigDecimal = bigDecimal.add(curs.geCurs());
        }
        bigDecimal = bigDecimal.divide(new BigDecimal(cursList.size()), BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        return bigDecimal;
    }
}
