package com.railwai.ivc.ivcschemabot.services;

import com.railwai.ivc.ivcschemabot.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class SendMessageService {

    private MessageSender messageSender;

    @Autowired
    public SendMessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void checkUser(Message message) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow kr = new KeyboardRow();
        kr.add(KeyboardButton.builder()
                .text("Send contact data")
                .requestContact(true).build());
        keyboardRows.add(kr);
        markup.setKeyboard(keyboardRows);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        SendMessage sm = new SendMessage();
        sm.setText("Authorization");
        sm.setChatId(String.valueOf(message.getChatId()));
        sm.setReplyMarkup(markup);
        messageSender.sendMessage(sm);
    }

    public void sendAuthorizationMessage(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("You are authorized. Hello " + message.getContact().getFirstName())
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }
}
