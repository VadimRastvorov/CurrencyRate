package ru.liga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActualCursTest {
    @BeforeEach
    public void setup() {
        //ParseCSV parseCSV = new ParseCSV("EUR");
    }
    @Test
    public void getCursEURTest(){
        ParseCSV parseCSV = new ParseCSV("EUR");
        ActualCurs actualCurs = new ActualCurs(parseCSV.getCursList());
    }
}
