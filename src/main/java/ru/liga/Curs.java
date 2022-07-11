package ru.liga;

import lombok.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * в этом классе постарался сделать Структуру файла для дальнейшей логики с данными
 */
@Getter
@Setter
@AllArgsConstructor
public class Curs {
    final Integer nominal;
    @With
    final LocalDate localDate;
    final BigDecimal curs;
    final String cdx;

    @SneakyThrows
    public Curs(String[] row) {
        this.nominal = Integer.parseInt(removeSpace(row[0]));
        this.localDate = LocalDate.parse(removeSpace(row[1]), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.curs = new BigDecimal(NumberFormat.getNumberInstance().parse(row[2]).toString());
        this.cdx = row[3];
    }
    @SneakyThrows
    public Curs(String nominal, String localDate, String curs, String cdx) {
        this.nominal = Integer.parseInt(removeSpace(nominal));
        this.localDate = LocalDate.parse(removeSpace(localDate), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.curs = new BigDecimal(NumberFormat.getNumberInstance().parse(curs).toString());
        this.cdx = cdx;
    }
    public String toString() {
        return String.format("%s - %s", localDate.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy")), curs);
    }

    private String removeSpace(String inString) {
        return inString.replace(" ", "");
    }
}
