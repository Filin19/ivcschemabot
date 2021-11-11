package com.railwai.ivc.ivcschemabot.bot;

import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IvcBot extends TelegramLongPollingBot {

    private SendMessageService sendMessageService;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
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
            if(message.hasText()) {
                sendMessageService.checkUser(message);
            } else if(message.hasContact()) {
                sendMessageService.sendAuthorizationMessage(message);
                System.out.println(message.getContact());
            }
        }
    }
}
