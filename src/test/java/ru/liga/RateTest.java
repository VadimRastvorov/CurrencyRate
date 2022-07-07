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
    public  void testGetRateTRYTomorrow() {
        assertThat(
                rate.getRate("TRY","tomorrow").isEmpty()).isFalse();
    }
    @Test
    public  void testGetRateTRYWeek() {
        assertThat(
                rate.getRate("TRY","week").isEmpty()).isFalse();
    }
    @Test
    public  void testGetRateEURTomorrow() {
        assertThat(
                rate.getRate("EUR","tomorrow").isEmpty()).isFalse();
    }
    @Test
    public  void testGetRateEURWeek() {
        assertThat(
                rate.getRate("EUR","week").isEmpty()).isFalse();
    }
    @Test
    public  void testGetRateUSDTomorrow() {
        assertThat(
                rate.getRate("USD","tomorrow").isEmpty()).isFalse();
    }
    @Test
    public  void testGetRateUSDWeek() {
        assertThat(
                rate.getRate("USD","week").isEmpty()).isFalse();
    }
}
