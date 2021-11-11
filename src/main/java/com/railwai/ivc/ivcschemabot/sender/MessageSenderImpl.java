package com.railwai.ivc.ivcschemabot.sender;

import com.railwai.ivc.ivcschemabot.bot.IvcBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderImpl implements MessageSender {

    private IvcBot bot;

    @Autowired
    public void setBot(IvcBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
