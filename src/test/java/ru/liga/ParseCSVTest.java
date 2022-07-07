package ru.liga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.file.ParseCSV;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseCSVTest {
    ParseCSV parseCSV;
    @BeforeEach
    public void setup() {
        //parseCSV = new ParseCSV("EUR");
        }
    @Test
    public void getCursEURTest(){
        assertThat(
                new ParseCSV("EUR").getCursList().isEmpty()).isFalse();
    }
    @Test
    public void getCursENUTest(){
        assertThat(
                new ParseCSV("USD").getCursList().isEmpty()).isFalse();
    }
    @Test
    public void getCursTRYTest(){
        assertThat(
                new ParseCSV("TRY").getCursList().isEmpty()).isFalse();
    }
}
