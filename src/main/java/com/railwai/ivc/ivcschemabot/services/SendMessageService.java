package com.railwai.ivc.ivcschemabot.services;

import com.railwai.ivc.ivcschemabot.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendMessageService {

    private final MessageSender messageSender;

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
        sm.setText("Войти могут только зарегестрированные пользователи." +
                " Отправьте, пожалуйста, свои контактные данные, для проверки регистрации");
        sm.setChatId(String.valueOf(message.getChatId()));
        sm.setReplyMarkup(markup);
        messageSender.sendMessage(sm);
    }

    public void sendAuthorizationMessage(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Авторизация успешна. Приветсвую " + message.getContact().getFirstName()
                + ". Выберите станцию или предприятие, для поиска схемы.")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    public void notAuthorized(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Вы не авторизированы. За подробностями, пожалуйста, обратитесь к администратору")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    public void sendStartKeyboard(Message message) {
        File startFolder = new File("./Схемы");
        File[] folderIneer = startFolder.listFiles();

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        int count = 1;
        KeyboardRow row = new KeyboardRow();
        for (File file : folderIneer) {
            row.add(KeyboardButton.builder().text(file.getName()).build());
            if (count == 3) {
                count = 0;
                keyboardRows.add(row);
                row = new KeyboardRow();
            }
            count++;
        }
        if(!row.isEmpty()) {
            keyboardRows.add(row);
        }
        markup.setKeyboard(keyboardRows);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        SendMessage sm = new SendMessage();
        sm.setText("Список");
        sm.setReplyMarkup(markup);
        sm.setChatId(String.valueOf(message.getChatId()));
        messageSender.sendMessage(sm);
    }
}
