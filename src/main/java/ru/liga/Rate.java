package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecast.CourseForecastMoon;
import ru.liga.forecast.CourseForecastMystical;
import ru.liga.forecast.CourseForecastWeek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Rate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rate.class);
    public StringBuilder messageOut = new StringBuilder();
    private String dateRegex = "(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
    private DateTimeFormatter datePattern =  DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * метод запуска основной логики
     *
     * @param inStr входящая строка
     */
    public void rateStart(String inStr) {
        ValidateInputString validateInputString = new ValidateInputString(inStr);
        if (validateInputString.listErrorCheck()) {
            for (String err : validateInputString.getListError()) {
                LOGGER.error(err);
                messageOut.append(err);
                messageOut.append(System.getProperty("line.separator"));
            }
            return;
        }
        for (Curs curs : getRate(
                validateInputString.getCurrency(),
                validateInputString.getPeriod(),
                validateInputString.getAlgorithm())) {
            LOGGER.info(curs.toString());
            messageOut.append(curs);
            messageOut.append(System.getProperty("line.separator"));
        }
    }

    /**
     * метод возвращает данные из запроса
     *
     * @param currency валюта
     * @param period   период
     * @return
     */
    public List<Curs> getRate(String currency, String period, String algorithm) {

        Integer periodDay = getPeriodDay(period);
        if (algorithm.equals("moon")) {
            CourseForecastMoon moon = new CourseForecastMoon();
            if (period.matches(dateRegex)) {
                return moon.getActualCursDayAlgMoon(currency,LocalDate.parse(period, datePattern));
            } else {
                return moon.getActualCursAlgMoon(currency, periodDay);
            }
        }
        if (algorithm.equals("mist")) {
            CourseForecastMystical mist = new CourseForecastMystical();
            if (period.matches(dateRegex)) {
                return mist.getCourseForecastDayAlgMist(currency,LocalDate.parse(period, datePattern));
            }else {
                return mist.getCourseForecastPeriodAlgMist(currency, periodDay);
            }
        }
        if (periodDay != 0) {
            CourseForecastWeek week = new CourseForecastWeek();
            return week.getCourseForecastPeriodAlgWeek(currency, periodDay);
        }
        if (period.matches(dateRegex)) {
            CourseForecastWeek week = new CourseForecastWeek();
            return week.getCourseForecastDate(currency, LocalDate.parse(period, datePattern));
        }
        return null;
    }

    private Integer getPeriodDay(String period) {
        switch (period) {
            case "tomorrow":
                return Period.DAY.period;
            case "week":
                return Period.WEEK.period;
            case "month":
                return Period.MONTH.period;
        }
        return 0;
    }
}
