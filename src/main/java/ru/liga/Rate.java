package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecast.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Rate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rate.class);
    public StringBuilder messageOut = new StringBuilder();
    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
                messageOut.append(err).append(System.getProperty("line.separator"));
            }
            return;
        }
        for (Curs curs : getRate(validateInputString.getCurrency(), validateInputString.getPeriod(), validateInputString.getAlgorithm())) {
            messageOut.append(curs).append(System.getProperty("line.separator"));
        }
    }

    /**
     * метод возвращает данные из запроса
     *
     * @param currency валюта
     * @param period   период
     * @return Возвращает лист курсов
     */
    public List<Curs> getRate(String currency, String period, String algorithm) {

        Integer periodDay = Period.valueOfPeriod(period).dayCount;
        String dateRegex = "(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)\\d\\d)$";
        if (algorithm.equals("moon")) {
            CourseForecast courseForecast = new CourseForecastMoon();
            if (period.matches(dateRegex)) {
                return courseForecast.getCourseForecastDay(currency, LocalDate.parse(period, datePattern));
            } else {
                return courseForecast.getCourseForecastPeriod(currency, periodDay);
            }
        }
        if (algorithm.equals("mist")) {
            CourseForecast courseForecast = new CourseForecastMystical();
            if (period.matches(dateRegex)) {
                return courseForecast.getCourseForecastDay(currency, LocalDate.parse(period, datePattern));
            } else {
                return courseForecast.getCourseForecastPeriod(currency, periodDay);
            }
        }if (algorithm.equals("internet")) {
            CourseForecast courseForecast = new CourseForecastLinearReg();
            if (period.matches(dateRegex)) {
                return courseForecast.getCourseForecastDay(currency, LocalDate.parse(period, datePattern));
            } else {
                return courseForecast.getCourseForecastPeriod(currency, periodDay);
            }
        }
        if (periodDay != 0) {
            CourseForecast week = new CourseForecastWeek();
            return week.getCourseForecastPeriod(currency, periodDay);
        }
        if (period.matches(dateRegex)) {
            CourseForecast week = new CourseForecastWeek();
            return week.getCourseForecastDay(currency, LocalDate.parse(period, datePattern));
        }
        return new ArrayList<>();
    }
}
