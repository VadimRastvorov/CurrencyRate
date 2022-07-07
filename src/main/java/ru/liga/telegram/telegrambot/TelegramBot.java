package ru.liga.telegram.telegrambot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.Rate;

public class TelegramBot extends TelegramLongPollingBot {

    @SneakyThrows
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public String getBotUsername() {
        return "@vadim_currency_rate_bot";
    }

    @Override
    public String getBotToken() {
        return "5574348245:AAHwJZ579w0bPIl5JfxDyQXLKH0jMgYTN4k";
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