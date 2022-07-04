package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rate {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rate.class);
    private final Integer LAST_WEEK = 7;
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
        }else if(period.matches("(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$")) {
            return getRateDate(currency, LocalDate.parse(period, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        return null;
    }

    private List<Curs> getRateDate(String currency, LocalDate date) {
        List<Curs> rateDate = new ArrayList<>();
        List<Curs> actualListCurs = getActualCurs(currency, date);
        if (actualListCurs.stream().anyMatch(a -> a.getLocalDate().isEqual(date))) {
            return actualListCurs.stream().filter(a -> a.getLocalDate().isEqual(date)).collect(Collectors.toList());
        }
        return actualListCurs;
    }

    private List<Curs> getRateTomorrow(String currency) {
        List<Curs> rateTomorrow = new ArrayList<>();
        LocalDate date = addLocalDateDay(LocalDate.now());
        List<Curs> actualListCurs = getActualCurs(currency, date);
        rateTomorrow.add(actualListCurs.get(0));
        return rateTomorrow;
    }

    private List<Curs> getRateWeek(String currency) {
        List<Curs> rateWeek = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        List<Curs> actualListCurs = getActualCurs(currency, localDate).subList(0, LAST_WEEK);
        for (int i = 0; i < actualListCurs.size(); i++) {
            localDate = addLocalDateDay(localDate);
            Curs curs = new Curs(
                    actualListCurs.get(0).getNominal(),
                    localDate,
                    getAverageCurs(actualListCurs),
                    actualListCurs.get(0).getCdx());
            rateWeek.add(curs);
            actualListCurs.add(0, curs);
            actualListCurs.remove(actualListCurs.size() - 1);
        }
        return rateWeek;
    }

    private List<Curs> getActualCurs(String currency, LocalDate date) {
        ParseCSV parse = new ParseCSV(currency);
        ActualCurs actualCurs = new ActualCurs(parse.getCursList());
        List<Curs> actualListCurs = actualCurs.actualCursList().stream().sorted((c1, c2) -> c2.getLocalDate().compareTo(c1.getLocalDate())).collect(Collectors.toList());
        if (actualListCurs.stream().anyMatch(a -> a.getLocalDate().isEqual(date))) {
            return actualListCurs;
        }
        while (!actualListCurs.get(0).getLocalDate().isEqual(date)) {
            LocalDate day = actualListCurs.get(0).getLocalDate().plusDays(1);
            Curs cursForDate = new Curs(
                    actualListCurs.get(0).getNominal(),
                    day,
                    getAverageCurs(actualListCurs.subList(0, LAST_WEEK)),
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

    private LocalDate addLocalDateDay(LocalDate date, int... days) {
        return date.plusDays(days.length > 0 ? days[0] : 1);
    }
}
