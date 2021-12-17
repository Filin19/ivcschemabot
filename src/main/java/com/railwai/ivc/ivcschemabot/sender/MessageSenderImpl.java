package com.railwai.ivc.ivcschemabot.sender;

import com.railwai.ivc.ivcschemabot.bot.IvcBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Class to send message to user.
 * <p>
 * @author Viktor Zaitsev.
 */
@Service
public class MessageSenderImpl implements MessageSender {

    /**
     * Variable to save IvcBot object.
     */
    private IvcBot bot;

    /**
     * Setter to IvcBot field.
     * <p>
     * @param bot IvcBot object.
     */
    @Autowired
    public void setBot(IvcBot bot) {
        this.bot = bot;
    }

    /**
     * Method to send message to user.
     * <p>
     * @param sendMessage SendMessage object.
     */
    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send picture of schema to user.
     * <p>
     * @param sendPhoto SendPhoto object.
     */
    @Override
    public void sendSchema(SendPhoto sendPhoto) {
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
