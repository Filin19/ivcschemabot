package com.railwai.ivc.ivcschemabot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    @Override
    public void choseAction(Message message) {

    }
}
