package ru.liga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * программа стартует отсюда. для понимания надо вводить строку начинающуюся с
 * rate
 * потом через пробел нужно ввечти Банковский код валюты, доступны:
 * EUR или AMD или BGN или KZT или TRY или USD
 *
 * В конце строки должно быть одно из значений "tomorrow" "week" "month" "year"
 * пример: rate KZT week
 *
 * пример 2: rate KZT -period week -alg moon
 * пример 3: rate KZT -date 22.10.2021 -alg moon
 * пример 4: rate KZT -period week -alg mist
 * пример 5: rate KZT -period week -alg internet
 *
 * чтобы ввыйти из программы введите "exit"
 */
public class CurrencyRate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRate.class);

    public static void main(String[] args) {
        LOGGER.debug("Запустили програму");
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Ввод запроса: ");
                String inStr = in.nextLine();
                if (inStr.contains("exit")) {
                    break;
                }
                Rate rate = new Rate();
                rate.rateStart(inStr);
                System.out.println(rate.messageOut);
            }
        }
    }
}