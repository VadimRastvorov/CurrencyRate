package ru.liga;

import java.util.ArrayList;
import java.util.List;

public class ValidateInputString {
    private final List<String> listError;

    private final String currency;
    private final String period;

    /**
     * в конструкторе провалидируем входящую строку
     * разобюем строку через пробел и каждый элемент проверим на адекватность
     * @param inStr строка которую надо проверить на валидность
     */
    public ValidateInputString(String inStr) {
        listError = new ArrayList<>();
        String[] inString = inStr.split(" ");
        if (!(inString.length == 3)) {
            listError.add("Вееденая строка не соответствует формату");
        }
        if (!inString[0].equals("rate")) {
            listError.add("Строка должна начинатся с \"rate\"");
        }
        if (!(inString[1].equals("TRY") || inString[1].equals("USD") || inString[1].equals("EUR"))) {
            listError.add("После \"rate \" должно быть одно из значений \"TRY\" \"USD\" \"EUR\"");
        }
        if (!(inString[2].equals("tomorrow") || inString[2].equals("week"))) {
            listError.add("В конце строки должно быть одно из значений \"tomorrow\" \"week\"");
        }
        currency = inString[1];
        period = inString[2];
    }

    public boolean listErrorCheck() {
        return !listError.isEmpty();
    }

    public List<String> getListError() {
        return listError;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPeriod() {
        return period;
    }
}
