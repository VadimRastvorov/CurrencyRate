package ru.liga;

import lombok.Getter;
import lombok.Setter;
import ru.liga.file.FilePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ValidateInputString {
    private List<String> listError;
    private String currency;

    private String[] currencies;
    private String period;
    private String algorithm;
    private String output;
    private final InputParams inputParams;

    /**
     * в конструкторе провалидируем входящую строку
     * разобюем строку через пробел и каждый элемент проверим на адекватность
     *
     * @param inStr строка которую надо проверить на валидность
     */
    public ValidateInputString(String inStr) {
        if (inStr.contains("-")) {
            ValidateInputStringNew(inStr);
        } else {
            ValidateInputStringOld(inStr);
        }
        inputParams = new InputParams(listError, currency, currency.split(","), period, algorithm, output);
    }

    public boolean listErrorCheck() {
        return !listError.isEmpty();
    }

    private void ValidateInputStringNew(String inStr) {
        listError = new ArrayList<>();
        String[] inString = inStr.split("\\s+");
        if (!inString[0].equals("rate")) {
            listError.add("Строка должна начинатся с \"rate\"");
        }
        if ((inString[1].length() != 3 && inString[1].length() != 7 && inString[1].length() != 11 && inString[1].length() != 15 && inString[1].length() != 19)) {
            listError.add("После \"rate \" должно быть одно из значений Банковского кода из 3 букв");
        } else {
            currencies = inString[1].split(",");
            currency = currencies[0];
            for (String curr : currencies) {
                FilePath filePath = new FilePath(curr);
                if (filePath.getFilePath() == null) {
                    listError.add(String.format("Не нашел файл валюты \"%s\"", curr));
                }
            }
        }
        for (int i = 2; i < inString.length; i++) {
            if (inString[i].equals("-date")) {
                period = inString[++i];
            }
            switch (inString[i]) {
                case "-date":
                case "-period":
                    period = inString[++i];
                    break;
                case "-alg":
                    algorithm = inString[++i];
                    break;
                case "-output":
                    output = inString[++i];
                    break;
            }
        }
        output = Optional.ofNullable(output).orElse("");
        algorithm = Optional.ofNullable(algorithm).orElse("");
        period = Optional.ofNullable(period).orElse("");
        currency = Optional.ofNullable(currency).orElse("");
    }

    private void ValidateInputStringOld(String inStr) {
        listError = new ArrayList<>();
        String[] inString = inStr.split("\\s+");
        if (inString.length != 3) {
            listError.add("Вееденая строка не соответствует формату");
            currency = "";
            period = "";
        } else {
            if (!inString[0].equals("rate")) {
                listError.add("Строка должна начинатся с \"rate\"");
            }
            if ((inString[1].length() != 3)) {
                listError.add("После \"rate \" должно быть одно из значений Банковского кода из 3 букв");
            } else {
                FilePath filePath = new FilePath(inString[1]);
                if (filePath.getFilePath() == null) {
                    listError.add(String.format("Не нашел файл валюты \"%s\"", inString[1]));
                }
            }
            if (!(inString[2].equals("tomorrow") || inString[2].equals("week") || inString[2].equals("month") || inString[2].equals("year"))) {
                listError.add("В конце строки должно быть одно из значений \"tomorrow\" \"week\" \"month\" \"year\"");
            }
            currency = Optional.ofNullable(inString[1]).orElse("");
            period = Optional.of(inString[2]).orElse("");
        }

        output = Optional.ofNullable(output).orElse("");
        algorithm = Optional.ofNullable(algorithm).orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValidateInputString validateInputString = (ValidateInputString) o;
        return
                currency.equals(validateInputString.currency) &&
                        period.equals(validateInputString.period) &&
                        output.equals(validateInputString.output) &&
                        algorithm.equals(validateInputString.algorithm);
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
