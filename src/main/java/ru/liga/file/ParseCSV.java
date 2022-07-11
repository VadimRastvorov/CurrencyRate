package ru.liga.file;

import au.com.bytecode.opencsv.CSVReader;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.Curs;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Прочтение файла csv и засунул его в массив курсов List<Curs>
 */
public class ParseCSV {
    private final Logger LOGGER = LoggerFactory.getLogger(ParseCSV.class);
    private final List<Curs> cursList;

    public ParseCSV(String currency) {
        cursList = getCurs(currency);
    }

    public List<Curs> getCursList() {
        return cursList;
    }

    /**
     * Функция возвращает структуру List<Curs> лист курсов по введенной валюте
     *
     * @param currency валюта
     * @return вернем массив валют List<Curs>
     */
    private List<Curs> getCurs(String currency) {
        List<Curs> curs = new ArrayList<>();
        FilePath filePath = new FilePath(currency);
        List<String[]> csvData = getFileData(filePath.getFilePath());
        String[] csvHeader = csvData.get(0);
        csvData.subList(1, csvData.size())
                .stream()
                .forEach(row -> curs.add(getCursFromHeader(row,csvHeader)));
        curs.sort(Comparator.comparing(Curs::getLocalDate).reversed());
        return curs;
    }

    private Curs getCursFromHeader(String[] curs, String[] header) {
        Map<String, String> headerValue = new HashMap<>();

        for(int i=0; i< header.length; i++)
        {
            headerValue.put(header[i],curs[i]);
        }
        return new Curs(headerValue.get("nominal"),
                headerValue.get("data"),
                headerValue.get("curs"),
                headerValue.get("cdx"));
    }

    /**
     * Прочитаем файл в массив построчно со 2 элемента так как 1 элемент эта шапка
     *
     * @param filePath полный путь к файлу
     * @return массив распарсенных строк List<String[]>
     */

    @SneakyThrows
    private List<String[]> getFileData(String filePath) {
        try (InputStreamReader streamReader = new InputStreamReader(Files.newInputStream(Paths.get(filePath)));
             CSVReader reader = new CSVReader(streamReader, ';', '"')) {
            return reader.readAll();
        }
    }
}