package ru.liga.telegram.telegrambot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.Rate;
import ru.liga.configuration.LoadProperties;

public class TelegramBot extends TelegramLongPollingBot {

    private LoadProperties properties;

    @SneakyThrows
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot();
        bot.properties = new LoadProperties();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public String getBotUsername() {
        return properties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getBotToken();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Rate rate = new Rate();
            rate.rateStart(message.getText());
            String outMessage = rate.messageOut.toString();
            if (message.hasText()) {
                execute(SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text(outMessage).build());
            }
        }
    }
}