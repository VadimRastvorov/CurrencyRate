package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Тут надо ввести строку пример: rate USD week
 * запустим логику прогноза
 */
public class CurrencyRate {
    private static final Logger LOGGER = LoggerFactory.getLogger( CurrencyRate.class );
    public static void main(String[] args)  {
        LOGGER.debug("Запустили програму");
        Scanner in = new Scanner(System.in);
        System.out.print("Ввод запроса: ");
        String inStr = in.nextLine();
        Rate rate = new Rate();
        rate.rateStart(inStr);
        in.close();
    }
}
