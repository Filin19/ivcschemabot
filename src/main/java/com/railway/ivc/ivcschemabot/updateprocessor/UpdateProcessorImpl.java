package com.railway.ivc.ivcschemabot.updateprocessor;

import com.railway.ivc.ivcschemabot.handler.CallbackQueryHandler;
import com.railway.ivc.ivcschemabot.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Class for processing Update object.
 * <p>
 * @author Viktor Zaitsev.
 */
@Component
public class UpdateProcessorImpl implements UpdateProcessor {

    /**
     * Variable to save MessageHandler object.
     */
    private final MessageHandler messageHandler;

    /**
     * Variable to save CallbackQueryHandler object.
     */
    private final CallbackQueryHandler callbackQueryHandler;

    /**
     * Constructor for class UpdateProcessorImpl.
     * <p>
     * @param messageHandler MessageHandler object.
     * @param callbackQueryHandler CallbackQueryHandler object.
     */
    @Autowired
    public UpdateProcessorImpl(MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    /**
     * Method to execute Message processing.
     * <p>
     * @param message Message object.
     */
    @Override
    public void executeMessage(Message message) {
        messageHandler.distributeMessage(message);
    }

    /**
     * method to execute CallbackQuery processing.
     * <p>
     * @param callbackQuery CallbackQuery object.
     */
    @Override
    public void executeCallBackQuery(CallbackQuery callbackQuery) {
        callbackQueryHandler.distributeMessage(callbackQuery);
    }
}
