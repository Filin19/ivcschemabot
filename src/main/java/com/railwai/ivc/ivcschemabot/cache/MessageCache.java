package com.railwai.ivc.ivcschemabot.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to caching login user.
 * <p>
 * @author Viktor Zaitsev.
 */
@Component
public class MessageCache {

    /**
     * Variable to save Map what using like cache.
     */
    private final Map<Long, String> cache;

    /**
     * Constructor for class MessageCache.
     * In Constructor was initialized Map for using in cache.
     */
    public MessageCache() {
        this.cache = new HashMap<>();
    }

    /**
     * Method to add new user to cache.
     * <p>
     * @param chat_id user chat id.
     * @param path path what user use at now time.
     */
    public void addNewUserToCache(Long chat_id, String path) {
        cache.put(chat_id, path);
    }

    /**
     * Method to change user path in existing cache.
     * <p>
     * @param chat_id user chat id.
     * @param path path where user want relocated.
     */
    public void addToExistingCache(Long chat_id, String path) {
        cache.put(chat_id, cache.get(chat_id) + path);
    }

    /**
     * Method to check is user was existing in cache.
     * <p>
     * @param chat_id user chat id.
     * @return boolean is user exist.
     */
    public boolean checkIfExist(Long chat_id) {
        return cache.containsKey(chat_id);
    }

    /**
     * Method to return path from cache, using user chat id.
     * <p>
     * @param chat_id user chat id.
     * @return path where was located user.
     */
    public String getFromCache(Long chat_id) {
        return cache.get(chat_id);
    }

    /**
     * Method to clean cache.
     */
    public void clearCache() {
        cache.clear();
    }
}
