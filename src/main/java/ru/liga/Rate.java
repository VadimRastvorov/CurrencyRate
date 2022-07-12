package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecast.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rate.class);
    public StringBuilder messageOut = new StringBuilder();
    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<Curs> cursList;
    public Map<String, List<Curs>> mapListCurs = new HashMap<>();

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
        cursList = getRate(validateInputString.getPeriod(), validateInputString.getAlgorithm(), validateInputString.getCurrencies());
        for (Curs curs : cursList) {
            messageOut.append(curs).append(System.getProperty("line.separator"));
        }
    }

    /**
     * метод возвращает данные из запроса
     *
     * @param currencies валюты
     * @param period   период
     * @return Возвращает лист курсов
     */
    public List<Curs> getRate(String period, String algorithm, String[] currencies) {

        Integer periodDay = Period.valueOfPeriod(period).dayCount;
        String dateRegex = "(0?[1-9]|[12]\\d|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)\\d\\d)$";
        for (String currency : currencies) {
            CourseForecast courseForecast;
            switch (algorithm) {
                case "moon":
                    courseForecast = new CourseForecastMoon();
                    break;
                case "mist":
                    courseForecast = new CourseForecastMystical();
                    break;
                case "internet":
                    courseForecast = new CourseForecastLinearReg();
                    break;
                case "week":
                default:
                    courseForecast = new CourseForecastWeek();
                    break;

            }
            if (period.matches(dateRegex)) {
                mapListCurs.put(currency, courseForecast.getCourseForecastDay(currency, LocalDate.parse(period, datePattern)));
            } else {
                mapListCurs.put(currency, courseForecast.getCourseForecastPeriod(currency, periodDay));
            }
        }
        return mapListCurs.get(currencies[0]);
    }
}
