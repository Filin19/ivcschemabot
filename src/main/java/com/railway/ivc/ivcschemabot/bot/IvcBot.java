package com.railway.ivc.ivcschemabot.bot;

import com.railway.ivc.ivcschemabot.updateprocessor.UpdateProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class what realized telegram bot.
 * <p>
 * @author Viktor Zaitsev.
 */
@Component
public class IvcBot extends TelegramLongPollingBot {

    /**
     * Variable to save UpdateProcessorImpl object.
     */
    private UpdateProcessorImpl updateProcessor;

    /**
     * Variable to save bot name.
     */
    @Value("${telegram.bot.username}")
    private String username;

    /**
     * Variable to save bot token.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Setter for UpdateProcessorImpl object.
     * <p>
     * @param updateProcessor UpdateProcessorImpl object.
     */
    @Autowired
    public void setUpdateProcessor(UpdateProcessorImpl updateProcessor) {
        this.updateProcessor = updateProcessor;
    }

    /**
     * Getter for username field
     * <p>
     * @return bot username.
     */
    @Override
    public String getBotUsername() {
        return username;
    }

    /**
     * Getter for bot token.
     * <p>
     * @return bot token.
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * Method to handle update taken from bot server.
     * <p>
     * @param update Update object.
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.handleUpdate(update);
    }
}
