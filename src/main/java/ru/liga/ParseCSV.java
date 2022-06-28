package ru.liga;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParseCSV {

    //������ ������ �� ����
    private String filePath(String currency) {
        String file = "";
        String filePathEUR = "./src/main/resources/RC_F01_06_2002_T17_06_2022_EUR.csv";
        String filePathTRY = "./src/main/resources/RC_F01_06_2002_T17_06_2022_TRY.csv";
        String filePathUSD = "./src/main/resources/RC_F01_06_2002_T17_06_2022_USD.csv";
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

    private List<String[]> getFileData(String filePath) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), "windows-1251"), ';', '"', 1);
            return  parseUTF8(reader.readAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<String[]> parseUTF8(List<String[]> list) {
        List<String[]> parseList = new ArrayList<>();
        for(String[] strings : list){
            String[] parsStrings = new String[strings.length];
            for (int i = 0 ; i< strings.length; i++){
                try {
                    parsStrings[i] = new String(strings[i].getBytes(StandardCharsets.UTF_8), "windows-1251");
                }catch (UnsupportedEncodingException e) {
                    parsStrings[i] = strings[i];
                }
            }
            parseList.add(parsStrings);
        }
        return parseList;
    }

    public List<Curs> getCurs(String currency) {
        List<Curs> curs = new ArrayList<>();
        for (String[] row : getFileData(filePath(currency))) {
            curs.add(new Curs(getInt(row[0]), getDate(row[1]), getDouble(row[2]), row[3]));
        }
        curs.sort((o1, o2) -> o2.date.compareTo(o1.date));
        return curs;
    }

    private LocalDate getDate(String dat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dat, formatter);
    }

    private Integer getInt(String strToInt) {
        return Integer.parseInt(strToInt.replace(" ", "").trim());
    }

    private Double getDouble(String strToDouble) {
        return Double.parseDouble(strToDouble.replace(" ", "").replace(',', '.').trim());
    }
}