package com.railway.ivc.ivcschemabot.handler;

import com.railway.ivc.ivcschemabot.services.SendMessageService;
import com.railway.ivc.ivcschemabot.services.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
     * Initial Logger Log4j for class CacheCleaner.
     */
    private static final Logger LOG = LogManager.getLogger(MessageHandler.class);

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
                LOG.info("user: " + message.getContact().getPhoneNumber()
                + " was authenticate. id: " + message.getChatId());
                sendMessageService.sendStartKeyboard(message);
            } else {
                LOG.info("authentication failed. number: "
                        + message.getContact().getPhoneNumber() + " | name:"
                        + message.getContact().getFirstName());
                sendMessageService.notAuthorized(message);
            }
        }
    }


}
