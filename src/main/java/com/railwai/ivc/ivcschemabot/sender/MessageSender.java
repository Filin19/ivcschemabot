package com.railwai.ivc.ivcschemabot.sender;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public interface MessageSender {

    void sendMessage(SendMessage sendMessage);
    void sendSchema(SendPhoto sendPhoto);
}
