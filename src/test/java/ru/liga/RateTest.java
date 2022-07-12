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
        rate.getRate("01.01.2050", "", new String[]{"KZT"});
    }
    @Test
    public void testRateStartNewTestDate() {
        rate.rateStart("rate KZT -date 22.02.2030");
    }

    @Test
    public void testRateStartNewTestWeek() {
        rate.rateStart("rate KZT -period week -alg mist");
    }
    @Test
    public void testRateStartNewTestMoonWeek() {
        rate.rateStart("rate KZT -period week -alg moon");
    }
    @Test
    public void testRateStartNewTestMoonMonth() {
        rate.rateStart("rate KZT -period month -alg moon");
    }
    @Test
    public void testRateStartTRYMonth() {
        rate.rateStart("rate TRY month");
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
        assertThat(rate.getRate("tomorrow", "", new String[]{"TRY"}).isEmpty()).isFalse();
    }

    @Test
    public void testGetRateTRYWeek() {
        assertThat(rate.getRate("week", "", new String[]{"TRY"}).isEmpty()).isFalse();
    }

    @Test
    public void testGetRateEURTomorrow() {
        assertThat(rate.getRate("tomorrow", "", new String[]{"EUR"}).isEmpty()).isFalse();
    }

    @Test
    public void testGetRateEURWeek() {
        assertThat(rate.getRate("week", "", new String[]{"EUR"}).isEmpty()).isFalse();
    }

    @Test
    public void testGetRateUSDTomorrow() {
        assertThat(rate.getRate("tomorrow", "", new String[]{"USD"}).isEmpty()).isFalse();
    }

    @Test
    public void testGetRateUSDWeek() {
        assertThat(rate.getRate("week", "", new String[]{"USD"}).isEmpty()).isFalse();
    }
}
