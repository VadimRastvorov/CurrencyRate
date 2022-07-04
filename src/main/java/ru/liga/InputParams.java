package ru.liga;

import java.util.List;
import java.util.Optional;

public class InputParams {
    private final List<String> listError;
    private final String currency;
    private final String[] currencies;
    private final String period;
    private final String algorithm;
    private final String output;

    public InputParams(List<String> listError, String currency, String period, String algorithm, String output) {
        this.listError = listError;
        this.currency = Optional.ofNullable(currency).orElse("");
        this.period = Optional.ofNullable(period).orElse("");
        this.algorithm = Optional.ofNullable(algorithm).orElse("");
        this.output = Optional.ofNullable(output).orElse("");
        this.currencies = Optional.ofNullable(currency).orElse("").split(",");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InputParams inputParams = (InputParams) o;
        return currency.equals(inputParams.currency) &&
                period.equals(inputParams.period) &&
                output.equals(inputParams.output) &&
                algorithm.equals(inputParams.algorithm);
    }
}
