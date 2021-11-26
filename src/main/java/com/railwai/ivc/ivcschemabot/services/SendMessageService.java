package com.railwai.ivc.ivcschemabot.services;

import com.railwai.ivc.ivcschemabot.cache.MessageCache;
import com.railwai.ivc.ivcschemabot.reader.FileReader;
import com.railwai.ivc.ivcschemabot.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendMessageService {

    private static final String START_FOLDER = "./Схемы";
    private final MessageCache messageCache;

    private final MessageSender messageSender;
    private final FileReader fileReader;

    @Autowired
    public SendMessageService(MessageSender messageSender,
                              FileReader fileReader, MessageCache messageCache) {
        this.fileReader = fileReader;
        this.messageSender = messageSender;
        this.messageCache = messageCache;
    }

    public void checkUser(Message message) {
        if(!messageCache.checkIfExist(message.getChatId())) {
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow kr = new KeyboardRow();
            kr.add(KeyboardButton.builder()
                    .text("Відправити дані")
                    .requestContact(true).build());
            keyboardRows.add(kr);
            markup.setKeyboard(keyboardRows);
            markup.setResizeKeyboard(true);
            markup.setOneTimeKeyboard(true);
            SendMessage sm = new SendMessage();
            sm.setText("Для того щоб почати роботу, відправте будь ласка ваші дані, для перевірки доступу");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            messageSender.sendMessage(sm);
        }
    }

    public void notAuthorized(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Ви не маєте доступу до інформації." +
                        " Зверніться до адміністратора за додатковими відомостями.")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    public void sendStartKeyboard(Message message) {
        messageCache.addNewUserToCache(message.getChatId(), START_FOLDER);
        List<File> folderIneer = fileReader.getFileList(START_FOLDER);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = generateButtonForKeyboard(folderIneer);

        markup.setKeyboard(keyboardRows);
        sendKeyboard(markup, message);
    }

    private List<List<InlineKeyboardButton>> generateButtonForKeyboard(List<File> folderInner) {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        int count = 1;
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (File file : folderInner) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(file.getName());
            button.setCallbackData("/" + file.getName());
            row.add(button);
            if (count == 3) {
                count = 0;
                keyboardRows.add(row);
                row = new ArrayList<>();
            }
            count++;
        }
        if(!row.isEmpty()) {
            keyboardRows.add(row);
        }
        return keyboardRows;
    }

    public void directionNavigate(Message message, String fileName) {
        messageCache.addToExistingCache(message.getChatId(), fileName);
        String path = messageCache.getFromCache(message.getChatId());
        List<File> folders = fileReader.getFileList(path);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = generateButtonForKeyboard(folders);
        keyboardRows.add(createBackButton());

        markup.setKeyboard(keyboardRows);
        sendKeyboard(markup, message);
    }

    public void wrongMessage(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Щось пішлол не так. Спробуйте знову")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    public void sendSchema(Message message, String fileName) {
        messageCache.addToExistingCache(message.getChatId(), fileName);
        SendPhoto sp = new SendPhoto();
        sp.setChatId(String.valueOf(message.getChatId()));
        sp.setPhoto(new InputFile(new File(messageCache.getFromCache(message.getChatId()))));
        messageSender.sendSchema(sp);
        sendBackButton(message);
    }

    private void sendKeyboard(InlineKeyboardMarkup markup, Message message) {
        SendMessage sm = new SendMessage();
        sm.setText("_____________________________");
        sm.setReplyMarkup(markup);
        sm.setChatId(String.valueOf(message.getChatId()));
        messageSender.sendMessage(sm);
    }

    private List<InlineKeyboardButton> createBackButton() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("повернутись до списку станцій");
        button.setCallbackData("/return");
        row.add(button);

        return row;
    }

    private void sendBackButton(Message message) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(createBackButton());
        markup.setKeyboard(keyboardRows);
        sendKeyboard(markup, message);
    }
}
