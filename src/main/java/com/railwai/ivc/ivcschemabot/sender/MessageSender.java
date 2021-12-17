package com.railwai.ivc.ivcschemabot.sender;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * Interface for class to sending message.
 * <p>
 * @author Viktor Zaitsev.
 */
public interface MessageSender {

    /**
     * Method to send message to user.
     * <p>
     * @param sendMessage SendMessage object.
     */
    void sendMessage(SendMessage sendMessage);

    /**
     * Method to send picture of schema to user.
     * <p>
     * @param sendPhoto SendPhoto object.
     */
    void sendSchema(SendPhoto sendPhoto);
}
