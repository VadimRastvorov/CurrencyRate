package ru.liga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseCSVTest {
    ParseCSV parseCSV;
    @BeforeEach
    public void setup() {
        parseCSV = new ParseCSV();
    }
    @Test
    public void getCursEURTest(){
        assertThat(
                parseCSV.getCurs("EUR").isEmpty()).isFalse();
    }
    @Test
    public void getCursENUTest(){
        assertThat(
                parseCSV.getCurs("USD").isEmpty()).isFalse();
    }
    @Test
    public void getCursTRYTest(){
        assertThat(
                parseCSV.getCurs("TRY").isEmpty()).isFalse();
    }
}
