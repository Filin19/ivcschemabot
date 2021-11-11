package com.railwai.ivc.ivcschemabot.updateprocessor;

import com.railwai.ivc.ivcschemabot.handler.CallbackQueryHandler;
import com.railwai.ivc.ivcschemabot.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UpdateProcessorImpl implements UpdateProcessor {

    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    @Autowired
    public UpdateProcessorImpl(MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public void executeMessage(Message message) {
        messageHandler.choseAction(message);
    }

    @Override
    public void executeCallBackQuery(CallbackQuery callbackQuery) {
        callbackQueryHandler.choseAction(callbackQuery);
    }
}
