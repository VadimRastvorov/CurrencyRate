package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rate.class);
    private final Integer LAST_DAY_COUNT = 7;
    private final Integer BIG_DECIMAL_SCALE = 4;

    public void rateStart(String inStr) {
        ValidateInputString validateInputString = new ValidateInputString(inStr);
        if (validateInputString.listErrorCheck()) {
            for (String err : validateInputString.getListError()) {
                LOGGER.error(err);
            }
            return;
        }
        Rate rate = new Rate();
        for (Curs curs : rate.getRate(validateInputString.getCurrency(), validateInputString.getPeriod())) {
            LOGGER.info(curs.toString());
        }
    }

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
        ParseCSV parse = new ParseCSV(currency);
        rateTomorrow.add(new Curs(
                1,
                addLocalDateDay(LocalDate.now()),
                getAverageCurs(parse.getCursList().subList(0, LAST_DAY_COUNT)),
                currency));
        return rateTomorrow;
    }

    private List<Curs> getRateWeek(String currency) {
        List<Curs> rateWeek = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        List<Curs> actualListCurs = actualCurs.actualCursList().subList(0, LAST_DAY_COUNT);
        for (int i = 0; i < actualListCurs.size(); i++) {
            localDate = addLocalDateDay(localDate);
            Curs cur = new Curs(1, localDate, getAverageCurs(actualListCurs), currency);
            rateWeek.add(cur);
            actualListCurs.add(0, cur);
            actualListCurs.remove(actualListCurs.size() - 1);
        }
        return rateWeek;
    }

    private BigDecimal getAverageCurs(List<Curs> cursList) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (Curs curs : cursList) {
            bigDecimal = bigDecimal.add(curs.geCurs());
        }
        bigDecimal = bigDecimal.divide(new BigDecimal(cursList.size()), BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
        return bigDecimal;
    }

    private LocalDate addLocalDateDay(LocalDate date, int... days) {
        return date.plusDays(days.length > 0 ? days[0] : 1);
    }
}
