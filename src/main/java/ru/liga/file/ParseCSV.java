package ru.liga.file;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.Curs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Постарался реализовать в этом классе прочтения файла csv и засунул его в массив курсов List<Curs>
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
     * @param currency валюта
     * @return вернем массив валют List<Curs>
     */
    private List<Curs> getCurs(String currency) {
        List<Curs> curs = new ArrayList<>();
        FilePath filePath = new FilePath(currency);
        for (String[] row : getFileData(filePath.getFilePath())) {
            curs.add(new Curs(row));
        }
        curs.sort((o1, o2) -> o2.getLocalDate().compareTo(o1.getLocalDate()));
        return curs;
    }

    /**
     * Прочитаем файл в массив построчно со 2 элемента так как 1 элемент эта шапка
     * @param filePath полный путь к файлу
     * @return массив распарсенных строк List<String[]>
     */
    private List<String[]> getFileData(String filePath) {
        try {
            InputStreamReader streamReader = new InputStreamReader(Files.newInputStream(Paths.get(filePath)), "windows-1251");
            CSVReader reader = new CSVReader(streamReader, ';', '"', 1);
            return parseInUTF8(reader.readAll());
        } catch (IOException e) {
            LOGGER.error("Ошибка в методе getFileData()");
            throw new RuntimeException(e);
        }
    }

    /**
     * перекодируем строки в UTF_8
     * @param list массив строк в кодировке (может быть отличной от UTF_8)
     * @return массив строк в кодировке UTF_8
     */
    private List<String[]> parseInUTF8(List<String[]> list) {
        List<String[]> parseList = new ArrayList<>();
        for (String[] strings : list) {
            String[] parsStrings = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                parsStrings[i] = new String(strings[i].getBytes(StandardCharsets.UTF_8));
            }
            parseList.add(parsStrings);
        }
        return parseList;
    }
}