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
    private String filePathEUR = "C:\\HomeWork\\CurrencyRate\\src\\main\\resources\\RC_F01_06_2002_T17_06_2022_EUR.csv";
    private String filePathTRY = "C:\\HomeWork\\CurrencyRate\\src\\main\\resources\\RC_F01_06_2002_T17_06_2022_TRY.csv";
    private String filePathUSD = "C:\\HomeWork\\CurrencyRate\\src\\main\\resources\\RC_F01_06_2002_T17_06_2022_USD.csv";

    //вернем ссылку на файл
    private String filePath(String currency) {
        if (currency.equals("EUR")) {
            return filePathEUR;
        } else if (currency.equals("TRY")) {
            return filePathTRY;
        } else if (currency.equals("USD")) {
            return filePathUSD;
        }
        return null;
    }

    //прочитаем файл в List<String[]> начиная с 1, так как 0 - наименования колонок
    private List<String[]> getFileData(String filePath) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath), ';', '"', 1);
        return reader.readAll();
    }

    //вернем структуру файла
    public List<Curs> getCurs(String currency) throws ParseException, IOException {
        List<Curs> curs = new ArrayList<Curs>();
        for (String[] row : getFileData(filePath(currency))) {
            curs.add(new Curs(getInt(row[0]), getDate(row[1]), getDouble(row[2]), row[3]));
        }
        //сортируем лист по дате
        Collections.sort(curs, (o1, o2) -> o2.date.compareTo(o1.date));
        return curs;
    }

    //Вернем дату, на вход строка в формате "dd.MM.yyyy"
    private Date getDate(String dat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.parse(dat);
    }

    //вернем Integer
    private Integer getInt(String strToInt) {
        return Integer.parseInt(strToInt.replace(" ", "").trim());
    }

    //вернем Double
    private Double getDouble(String strToDouble) {
        return Double.parseDouble(strToDouble.replace(" ", "").replace(',', '.').trim());
    }
}