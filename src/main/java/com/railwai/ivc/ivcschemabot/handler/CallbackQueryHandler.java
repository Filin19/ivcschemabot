package com.railwai.ivc.ivcschemabot.handler;

import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private SendMessageService sendMessageService;

    @Autowired
    public CallbackQueryHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    /**
     * Метод для обработки CallbackQuery. Если выбрано название станции
     * то в сервис передается ид месседжа и название станции.
     * @param callbackQuery
     */
    @Override
    public void distributeMessage(CallbackQuery callbackQuery) {
        System.out.println(callbackQuery.getData());
        if(callbackQuery.getData().equals("/return")) {
            sendMessageService.sendStartKeyboard(callbackQuery.getMessage());
        } else if(callbackQuery.getData().charAt(0) == '/') {
            if(callbackQuery.getData().endsWith(".jpg")) {
                sendMessageService.sendSchema(callbackQuery.getMessage(),
                        callbackQuery.getData());
            } else {
                sendMessageService.directionNavigate(callbackQuery.getMessage(),
                        callbackQuery.getData());
            }
        } else {
            sendMessageService.wrongMessage(callbackQuery.getMessage());
        }
    }
}
