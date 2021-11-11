package com.railwai.ivc.ivcschemabot.updateprocessor;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProcessor {

    void executeMessage(Message message);

    void executeCallBackQuery(CallbackQuery callbackQuery);

    default void handleUpdate(Update update) {
        if(update.hasMessage()) {
            executeMessage(update.getMessage());
        }
        if(update.hasCallbackQuery()) {
            executeCallBackQuery(update.getCallbackQuery());
        }
    }
}
