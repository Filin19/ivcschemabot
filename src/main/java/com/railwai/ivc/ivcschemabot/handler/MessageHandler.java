package com.railwai.ivc.ivcschemabot.handler;

import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import com.railwai.ivc.ivcschemabot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler implements Handler<Message> {

    private final SendMessageService sendMessageService;
    private final UserService userService;

    @Autowired
    public MessageHandler(SendMessageService sendMessageService, UserService userService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    @Override
    public void distributeMessage(Message message) {
        if(message.hasText()) {
            sendMessageService.checkUser(message);
        }
        if(message.hasContact()) {
            if(userService.checkUser(message.getContact())) {
                sendMessageService.sendAuthorizationMessage(message);
                sendMessageService.sendStartKeyboard(message);
            } else {
                sendMessageService.notAuthorized(message);
            }
            System.out.println(message.getContact());
        }
    }


}
