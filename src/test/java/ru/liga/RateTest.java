package ru.liga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RateTest {
    Rate rate;

    @BeforeEach
    public void setup() {
        rate = new Rate();
    }
    @Test
    public void getRateTest() {
        rate.getRate("KZT", "01.01.2050");
    }

    @Test
    public void testRateStartNewTest() {
        rate.rateStart("rate KZT -period week -alg mist");
    }
    @Test
    public void testRateStartTRYTomorrow() {
        rate.rateStart("rate TRY tomorrow");
    }
    @Test
    public void testRateStartEURTomorrow() {
        rate.rateStart("rate EUR tomorrow");
    }
    @Test
    public void testRateStartUSDTomorrow() {
        rate.rateStart("rate USD tomorrow");
    }
    @Test
    public void testRateStartKZTTomorrow() {
        rate.rateStart("rate KZT tomorrow");
    }
    @Test
    public void testRateStartTRYWeek() {
        rate.rateStart("rate TRY week");
    }
    @Test
    public void testRateStartEURWeek() {
        rate.rateStart("rate EUR week");
    }
    @Test
    public void testRateStartUSDWeek() {
        rate.rateStart("rate USD week");
    }

    @Test
    public void testGetRateTRYTomorrow() {
        assertThat(rate.getRate("TRY", "tomorrow").isEmpty()).isFalse();
    }

    @Test
    public void testGetRateTRYWeek() {
        assertThat(rate.getRate("TRY", "week").isEmpty()).isFalse();
    }

    @Test
    public void testGetRateEURTomorrow() {
        assertThat(rate.getRate("EUR", "tomorrow").isEmpty()).isFalse();
    }

    @Test
    public void testGetRateEURWeek() {
        assertThat(rate.getRate("EUR", "week").isEmpty()).isFalse();
    }

    @Test
    public void testGetRateUSDTomorrow() {
        assertThat(rate.getRate("USD", "tomorrow").isEmpty()).isFalse();
    }

    @Test
    public void testGetRateUSDWeek() {
        assertThat(rate.getRate("USD", "week").isEmpty()).isFalse();
    }
}
