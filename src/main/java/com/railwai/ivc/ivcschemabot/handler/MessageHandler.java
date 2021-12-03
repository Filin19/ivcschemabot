package com.railwai.ivc.ivcschemabot.handler;

import com.railwai.ivc.ivcschemabot.cache.MessageCache;
import com.railwai.ivc.ivcschemabot.services.SendMessageService;
import com.railwai.ivc.ivcschemabot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Class to handling message getting from user.
 * <p>
 * @author Viktor Zaitsev
 */
@Component
public class MessageHandler implements Handler<Message> {

    /**
     * Variable to save SendMessageService object.
     */
    private final SendMessageService sendMessageService;

    /**
     * Variable to save UserService object.
     */
    private final UserService userService;

    /**
     * Constructor for class MessageHandler
     * <p>
     * @param sendMessageService SendMessageService object.
     * @param userService UserService object.
     */
    @Autowired
    public MessageHandler(SendMessageService sendMessageService, UserService userService) {
        this.sendMessageService = sendMessageService;
        this.userService = userService;
    }

    /**
     * Method to handling user message.
     * <p>
     * @param message object Message.
     */
    @Override
    public void distributeMessage(Message message) {
        if(message.hasText()) {
            sendMessageService.checkUser(message);
        }
        if(message.hasContact()) {
            if(userService.checkUser(message.getContact())) {
                sendMessageService.sendStartKeyboard(message);
            } else {
                sendMessageService.notAuthorized(message);
            }
            System.out.println(message.getContact());  // remove when program was ready
        }
    }


}
