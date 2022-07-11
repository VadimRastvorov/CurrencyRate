package ru.liga.configuration;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public class LoadProperties {
    private final Properties addProperties;
    public static final Integer BIG_DECIMAL_SCALE = 4;

    @SneakyThrows
    public LoadProperties() {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String defaultConfigPath = rootPath + "app.properties";
        addProperties = new Properties();
        addProperties.load(new FileInputStream(defaultConfigPath));
    }

    public String getBotUsername() {
        return addProperties.getProperty("BotUsername");
    }

    public String getBotToken() {
        return addProperties.getProperty("BotToken");
    }
}
