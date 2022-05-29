package com.railway.ivc.ivcschemabot.updateprocessor;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Interface to processing update.
 * <p>
 * @author Viktor Zaitsev.
 */
public interface UpdateProcessor {

    /**
     * Method to execute Message processing.
     * <p>
     * @param message Message object.
     */
    void executeMessage(Message message);

    /**
     * method to execute CallbackQuery processing.
     * <p>
     * @param callbackQuery CallbackQuery object.
     */
    void executeCallBackQuery(CallbackQuery callbackQuery);

    /**
     * Default method for defining actions on an update object.
     * <p>
     * @param update Update object.
     */
    default void handleUpdate(Update update) {
        if(update.hasMessage()) {
            executeMessage(update.getMessage());
        }
        if(update.hasCallbackQuery()) {
            executeCallBackQuery(update.getCallbackQuery());
        }
    }
}
