package ru.liga.jfreechart;

import org.jfree.ui.RefineryUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.CurrencyRate;
import ru.liga.Rate;

import java.util.Scanner;

public class JfreeChartMain {
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

                JfreeChart jfreeChart = new JfreeChart("Курс Влют", rate.mapListCurs);
                jfreeChart.pack();
                RefineryUtilities.centerFrameOnScreen(jfreeChart);
                jfreeChart.setVisible(true);
            }
        }
    }
}
