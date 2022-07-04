package ru.liga;

import java.util.List;

public class InputParams {
    private final List<String> listError;
    private final String currency;
    private final String[] currencies;
    private final String period;
    private final String algorithm;
    private final String output;

    public InputParams(List<String> listError, String currency, String period, String algorithm, String output) {
        this.listError = listError;
        this.currency = currency;
        this.period = period;
        this.algorithm = algorithm;
        this.output = output;
        this.currencies = currency.split(",");
    }
}
