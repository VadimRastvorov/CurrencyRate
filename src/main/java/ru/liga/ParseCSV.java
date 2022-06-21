package ru.liga;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ParseCSV {
    private final String filePathEUR = "./src/main/resources/RC_F01_06_2002_T17_06_2022_EUR.csv";
    private final String filePathTRY = "./src/main/resources/RC_F01_06_2002_T17_06_2022_TRY.csv";
    private final String filePathUSD = "./src/main/resources/RC_F01_06_2002_T17_06_2022_USD.csv";

    //������ ������ �� ����
    private String filePath(String currency) {
        String file = "";
        switch (currency) {
            case "EUR":
                file = filePathEUR;
                break;
            case "TRY":
                file = filePathTRY;
                break;
            case "USD":
                file = filePathUSD;
                break;
        }
        return file;
    }

    //��������� ���� � List<String[]> ������� � 1, ��� ��� 0 - ������������ �������
    private List<String[]> getFileData(String filePath) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath), ';', '"', 1);
        return reader.readAll();
    }

    //������ ��������� �����
    public List<Curs> getCurs(String currency) throws ParseException, IOException {
        List<Curs> curs = new ArrayList<Curs>();
        for (String[] row : getFileData(filePath(currency))) {
            curs.add(new Curs(getInt(row[0]), getDate(row[1]), getDouble(row[2]), row[3]));
        }
        //��������� ���� �� ����
        Collections.sort(curs, (o1, o2) -> o2.date.compareTo(o1.date));
        return curs;
    }

    //������ ����, �� ���� ������ � ������� "dd.MM.yyyy"
    private Date getDate(String dat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.parse(dat);
    }

    //������ Integer
    private Integer getInt(String strToInt) {
        return Integer.parseInt(strToInt.replace(" ", "").trim());
    }

    //������ Double
    private Double getDouble(String strToDouble) {
        return Double.parseDouble(strToDouble.replace(" ", "").replace(',', '.').trim());
    }
}