package ru.liga.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class FilePath {
    private static final String DIRECTORY = "./src/main/resources/csv/";
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePath.class);
    private final String file;

    public FilePath(String currency) {
        file = getFileWithCurrency(currency);
        LOGGER.info("Подобрали файл из ресурсов {}", file);
    }

    public String getFilePath() {
        return file;
    }

    /**
     * Функция подбирает файл из коталога по введенной валюте
     *
     * @param currency валюта.....................
     * @return вернем первый попавшийся файл с валютой
     */
    private String getFileWithCurrency(String currency) {
        List<Path> files = getAllFilesInDirectory().stream()
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().contains(currency))
                .collect(Collectors.toList());
        if(files.isEmpty()) return null;
        return files.get(0).toString();
    }

    private List<Path> getAllFilesInDirectory() {
        try (Stream<Path> paths = Files.walk(Paths.get(DIRECTORY))) {
            return paths.collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Ошибка в конструкторе FilePath(), при чтении каталога {}", e);
            throw new RuntimeException(e);
        }
    }
}
