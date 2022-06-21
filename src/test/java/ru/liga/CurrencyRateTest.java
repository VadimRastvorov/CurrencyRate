package ru.liga;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

public class CurrencyRateTest {
    @Test
    public void testCurrencyRate() throws ParseException, IOException {
        CurrencyRate.RateStart("rate TRY tomorrow");
        CurrencyRate.RateStart("rate TRY week");
        CurrencyRate.RateStart("rate USD tomorrow");
        CurrencyRate.RateStart("rate USD week");
        CurrencyRate.RateStart("rate EUR tomorrow");
        CurrencyRate.RateStart("rate EUR week");
    }
}
