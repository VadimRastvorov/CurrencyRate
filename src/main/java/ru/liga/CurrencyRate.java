package ru.liga;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyRate {
    static List<String> listError;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Ввод запроса: ");
        String inStr = in.nextLine();
        rateStart(inStr);
        in.close();
    }
    private static String[] validateString(String inStr) {
        listError = new ArrayList<>();
        String[] subStr = inStr.split(" ");
        if (!(subStr.length == 3)) {
            listError.add("Вееденая строка не соответствует формату");
        }
        if (!subStr[0].equals("rate")) {
            listError.add("Строка должна начинатся с \"rate\"");
        }
        if (!(subStr[1].equals("TRY") || subStr[1].equals("USD") || subStr[1].equals("EUR"))) {
            listError.add("После \"rate \" должно быть одно из значений \"TRY\" \"USD\" \"EUR\"");
        }
        if (!(subStr[2].equals("tomorrow") || subStr[2].equals("week"))) {
            listError.add("В конце строки должно быть одно из значений \"tomorrow\" \"week\"");
        }
        return subStr;
    }

    public static void rateStart(String inStr) {
        String[] subStr = validateString(inStr); //"rate TRY tomorrow");
        if (listError.size() != 0) {
            for (String err : listError) {
                System.out.println(err);
            }
            return;
        }
        Rate rate = new Rate();
        for (Curs curs : rate.getRate(subStr[1], subStr[2])) {
            System.out.printf("%s\t%s%n", curs.getLocalDate().format(DateTimeFormatter.ofPattern("E dd.MM.yyyy")), String.format("%.4f", curs.geCurs()));
        }
    }
}
