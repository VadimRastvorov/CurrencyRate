package ru.liga;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidateInputStringTest {
    @Test
    public void testValidateInputString() {

        assertThat(new ValidateInputString("rate TRY -date tomorrow -alg mist -output List").getInputParams().equals(new InputParams(
                null,
                "TRY",
                null,
                "tomorrow",
                "mist",
                "List"))).isTrue();

        assertThat(new ValidateInputString("rate TRY -date 22.02.2030 -alg mist").getInputParams().equals(new InputParams(
                null,
                "TRY",
                "TRY".split(","),
                "22.02.2030",
                "mist",
                ""))).isTrue();
        assertThat(new ValidateInputString("rate USD -period week -alg mist -output list").getInputParams().equals(new InputParams(
                null,
                "USD",
                null,
                "week",
                "mist",
                "list"))).isTrue();
        assertThat(new ValidateInputString("rate USD,TRY -period month -alg moon -output graph").getInputParams().equals(new InputParams(
                null,
                "USD",
                null,
                "month",
                "moon",
                "graph"))).isTrue();
    }
}
