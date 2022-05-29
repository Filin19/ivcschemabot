package com.railway.ivc.ivcschemabot.services;

import com.railway.ivc.ivcschemabot.cache.MessageCache;
import com.railway.ivc.ivcschemabot.reader.FileReader;
import com.railway.ivc.ivcschemabot.sender.MessageSender;
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
import java.util.List;

/**
 * Class Service for sending message to user.
 * <p>
 * @author Viktor Zaitsev.
 */
@Service
public class SendMessageService {

    /**
     * Constant to save entry point to folder three.
     */
    private static final String START_FOLDER = "./Схемы";

    /**
     * Variable to save MessageCache object.
     */
    private final MessageCache messageCache;

    /**
     * Variable to save MessageSender object.
     */
    private final MessageSender messageSender;

    /**
     * Variable to save FileReader object.
     */
    private final FileReader fileReader;

    /**
     * Constructor for class SendMessageService.
     * <p>
     * @param messageSender MessageSender object.
     * @param fileReader FileReader object.
     * @param messageCache MessageCache object.
     */
    @Autowired
    public SendMessageService(MessageSender messageSender,
                              FileReader fileReader, MessageCache messageCache) {
        this.fileReader = fileReader;
        this.messageSender = messageSender;
        this.messageCache = messageCache;
    }

    /**
     * Method for send user a message to enter his authorization data.
     * <p>
     * @param message Message object.
     */
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

    /**
     * Method send message to user if he is not authorized.
     * <p>
     * @param message Message object.
     */
    public void notAuthorized(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Ви не маєте доступу до інформації." +
                        " Зверніться до адміністратора за додатковими відомостями.")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    /**
     * Method sends to user start keyboard to chose station.
     * <p>
     * @param message Message object.
     */
    public void sendStartKeyboard(Message message) {
        messageCache.addNewUserToCache(message.getChatId(), START_FOLDER);
        List<File> folderIneer = fileReader.getFileList(START_FOLDER);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = generateButtonForKeyboard(folderIneer);

        markup.setKeyboard(keyboardRows);
        sendKeyboard(markup, message);
    }

    /**
     * Method generate button from keyboard using List of file from argument.
     * <p>
     * @param folderInner List of file.
     * @return Keyboard with button are repeating file names.
     */
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

    /**
     * Method relocate user to the next folder in three.
     * <p>
     * @param message Message object.
     * @param fileName fileName where user was relocated.
     */
    public void changeFolder(Message message, String fileName) {
            messageCache.addToExistingCache(message.getChatId(), fileName);
            String path = messageCache.getFromCache(message.getChatId());
            List<File> folders = fileReader.getFileList(path);

            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboardRows = generateButtonForKeyboard(folders);
            keyboardRows.add(createBackButton());

            markup.setKeyboard(keyboardRows);
            sendKeyboard(markup, message);
    }

    /**
     * Method sends to user error message.
     * <p>
     * @param message Message object.
     */
    public void wrongMessage(Message message) {
        SendMessage sm = SendMessage.builder()
                .text("Щось пішлол не так. Спробуйте знову")
                .chatId(String.valueOf(message.getChatId()))
                .build();
        messageSender.sendMessage(sm);
    }

    /**
     * Method to send picture of the schema to user.
     * <p>
     * @param message Message object.
     * @param fileName fileName object.
     */
    public void sendSchema(Message message, String fileName) {
        messageCache.addToExistingCache(message.getChatId(), fileName);
        SendPhoto sp = new SendPhoto();
        sp.setChatId(String.valueOf(message.getChatId()));
        sp.setPhoto(new InputFile(new File(messageCache.getFromCache(message.getChatId()))));
        messageSender.sendSchema(sp);
        sendBackButton(message);
    }

    /**
     * Method to send keyboard to user.
     * <p>
     * @param markup InlineKeyboardMarkup object.
     * @param message Message object.
     */
    private void sendKeyboard(InlineKeyboardMarkup markup, Message message) {
        SendMessage sm = new SendMessage();
        sm.setText("_____________________________");
        sm.setReplyMarkup(markup);
        sm.setChatId(String.valueOf(message.getChatId()));
        messageSender.sendMessage(sm);
    }

    /**
     * Method to create button for redirection user to start keyboard.
     * <p>
     * @return button with redirection.
     */
    private List<InlineKeyboardButton> createBackButton() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("повернутись до списку станцій");
        button.setCallbackData("/return");
        row.add(button);

        return row;
    }

    /**
     * Method to send user button for redirection on start keyboard.
     * <p>
     * @param message Message object.
     */
    private void sendBackButton(Message message) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        keyboardRows.add(createBackButton());
        markup.setKeyboard(keyboardRows);
        sendKeyboard(markup, message);
    }
}
