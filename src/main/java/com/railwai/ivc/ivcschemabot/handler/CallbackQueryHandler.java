package com.railwai.ivc.ivcschemabot.handler;

import com.railwai.ivc.ivcschemabot.cache.MessageCache;
import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Class CallbackQueryHandler used to handle callbackQuery.
 * What a twist :P
 * <p>
 * @author Viktor Zaitsev.
 */
@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    /**
     * Variable to save SendMessageService object.
     */
    private final SendMessageService sendMessageService;

    /**
     * Variable to save MessageCache object.
     */
    private final MessageCache messageCache;

    /**
     * Constructor for class CallbackQueryHandler
     * <p>
     * @param sendMessageService SendMessageService object.
     * @param messageCache MessageCache object.
     */
    @Autowired
    public CallbackQueryHandler(SendMessageService sendMessageService, MessageCache messageCache) {
        this.sendMessageService = sendMessageService;
        this.messageCache = messageCache;
    }

    // NEED TO REFACTOR!!!!!!

    /**
     * Method to handle CallbackQuery. If chosen station name,
     * method transmit to service message id and station name.
     *<p>
     * @param callbackQuery CallbackQuery object.
     */
    @Override
    public void distributeMessage(CallbackQuery callbackQuery) {
        if(isUserInCache(callbackQuery)) {
            System.out.println(callbackQuery.getData());   // delete when program was ready
            checkData(callbackQuery);
        } else {
            sendMessageService.checkUser(callbackQuery.getMessage());
        }
    }

    /**
     * Method check is cache has this user.
     * <p>
     * @param callbackQuery CallbackQuery object.
     * @return boolean value is cache has user.
     */
    private boolean isUserInCache(CallbackQuery callbackQuery) {
        return messageCache.checkIfExist(callbackQuery.getMessage().getChatId());
    }

    /**
     * Method processing callbackQuery data.
     * <p>
     * @param callbackQuery CallbackQuery object.
     */
    private void checkData(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals("/return")) {
            sendMessageService.sendStartKeyboard(callbackQuery.getMessage());
        } else if (callbackQuery.getData().charAt(0) == '/') {
            answerReturn(callbackQuery);
        } else {
            sendMessageService.wrongMessage(callbackQuery.getMessage());
        }
    }

    /**
     * Method check is data was address of folder or picture. And return to user
     * new keyboard or picture.
     * <p>
     * @param callbackQuery CallbackQuery object.
     */
    private void answerReturn(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().endsWith(".jpg")) {
            sendMessageService.sendSchema(callbackQuery.getMessage(),
                    callbackQuery.getData());
        } else {
            sendMessageService.changeFolder(callbackQuery.getMessage(),
                    callbackQuery.getData());
        }
    }
}
