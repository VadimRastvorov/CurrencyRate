package ru.liga;

import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * в этом классе постарался сделать Структуру файла для дальнейшей логики с данными
 */
public class Curs {
    final Integer nominal;
    @With
    final LocalDate localDate;
    final BigDecimal curs;
    final String cdx;

    public Curs(Integer nominal, LocalDate localDate, BigDecimal curs, String cdx) {
        this.nominal = nominal;
        this.localDate = localDate;
        this.curs = curs;
        this.cdx = cdx;
    }

    public Curs(String[] row) {
        this.nominal = Integer.parseInt(removeSpace(row[0]));
        this.localDate = LocalDate.parse(removeSpace(row[1]), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.curs = new BigDecimal(removeSpace(row[2]).replace(',', '.'));
        this.cdx = row[3];
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public BigDecimal geCurs() {
        return curs;
    }

    public String getCdx() {
        return cdx;
    }

    public String toString() {
        return  String.format("%s\t%s", localDate.format(DateTimeFormatter.ofPattern("E dd.MM.yyyy")), curs);
    }

    private String removeSpace(String inString) {
        return inString.replace(" ", "");
    }
}
