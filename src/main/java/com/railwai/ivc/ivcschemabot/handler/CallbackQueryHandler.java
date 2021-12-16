package com.railwai.ivc.ivcschemabot.handler;

import com.railwai.ivc.ivcschemabot.cache.MessageCache;
import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private final SendMessageService sendMessageService;

    private final MessageCache messageCache;

    @Autowired
    public CallbackQueryHandler(SendMessageService sendMessageService, MessageCache messageCache) {
        this.sendMessageService = sendMessageService;
        this.messageCache = messageCache;
    }

    // NEED TO REFACTOR!!!!!!

    /**
     * Метод для обработки CallbackQuery. Если выбрано название станции
     * то в сервис передается ид месседжа и название станции.
     *
     * @param callbackQuery
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
     * @param callbackQuery
     * @return
     */
    private boolean isUserInCache(CallbackQuery callbackQuery) {
        return messageCache.checkIfExist(callbackQuery.getMessage().getChatId());
    }

    /**
     * Method processing callbackQuery data.
     * @param callbackQuery
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
     * @param callbackQuery
     */
    private void answerReturn(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().endsWith(".jpg")) {
            sendMessageService.sendSchema(callbackQuery.getMessage(),
                    callbackQuery.getData());
        } else {
            sendMessageService.directionNavigate(callbackQuery.getMessage(),
                    callbackQuery.getData());
        }
    }
}
