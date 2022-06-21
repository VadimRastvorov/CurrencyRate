package ru.liga;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CurrencyRate {
    private static String[] validateString(String inStr) {

        String[] subStr = inStr.split(" ");
        if (subStr.length == 3) {
            if (!subStr[0].equals("rate")) {
                System.out.println("Строка должна начинатся с \"rate\"" + "  "+subStr[0]);
                return null;
            }
            if (!(subStr[1].equals("TRY") || subStr[1].equals("USD") || subStr[1].equals("EUR"))){
                System.out.println("После \"rate \" должно быть одно из значений \"TRY\" \"USD\" \"EUR\"");
                return null;
            }
            if (!(subStr[2].equals("tomorrow") || subStr[2].equals("week"))){
                System.out.println("В конце должно быть одно из значений \"tomorrow\" \"week\"");
                return null;
            }
        }else {
            System.out.println("Вееденая строка не соответствует формату");
            return null;
        }
        return subStr;
    }

    public static void main(String[] args) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Ввод запроса: ");
        String inStr = in.nextLine();
        RateStart(inStr);
        in.close();
    }
    public static void RateStart(String inStr) throws ParseException, IOException {
        String[] subStr = validateString(inStr); //"rate TRY tomorrow");
        if(subStr != null)
        {
            Rate rate = new Rate();
            for(Curs curs:rate.getRate(subStr[1].toString(), subStr[2].toString())){
                System.out.println(String.format("%s\t%s", new SimpleDateFormat("E dd.MM.yyyy").format(curs.date), String.format("%.4f",curs.curs)));
            }
        }
    }
}
