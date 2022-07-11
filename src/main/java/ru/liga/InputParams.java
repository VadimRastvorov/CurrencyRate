package ru.liga;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InputParams {
    private final List<String> listError;
    private final String currency;
    private final String[] currencies;
    private final String period;
    private final String algorithm;
    private final String output;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        result = prime * result + ((output == null) ? 0 : output.hashCode());
        result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
        return result;
    }
}
