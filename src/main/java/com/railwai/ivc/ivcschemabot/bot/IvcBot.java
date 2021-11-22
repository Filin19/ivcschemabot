package com.railwai.ivc.ivcschemabot.bot;

import com.railwai.ivc.ivcschemabot.handler.CallbackQueryHandler;
import com.railwai.ivc.ivcschemabot.handler.MessageHandler;
import com.railwai.ivc.ivcschemabot.updateprocessor.UpdateProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IvcBot extends TelegramLongPollingBot {

    private UpdateProcessorImpl updateProcessor;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    public void setUpdateProcessor(UpdateProcessorImpl updateProcessor) {
        this.updateProcessor = updateProcessor;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            updateProcessor.executeMessage(message);
        }
        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            updateProcessor.executeCallBackQuery(callbackQuery);
        }
    }
}
