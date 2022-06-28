package ru.liga;

import org.junit.jupiter.api.Test;

public class CurrencyRateTest {
    @Test
    public void testCurrencyRate() {
        CurrencyRate.rateStart("rate TR2Y tomorrow");
        CurrencyRate.rateStart("rate T2RY week");
        CurrencyRate.rateStart("rate USD tomorrow");
        CurrencyRate.rateStart("rate USD week");
        CurrencyRate.rateStart("rate EUR tomorrow");
        CurrencyRate.rateStart("rate EUR week");
    }
}
