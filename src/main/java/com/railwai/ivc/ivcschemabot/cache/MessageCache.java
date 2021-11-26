package com.railwai.ivc.ivcschemabot.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageCache {

    private final Map<Long, String> cache;

    public MessageCache() {
        this.cache = new HashMap<>();
    }

    public void addNewUserToCache(Long chat_id, String path) {
        cache.put(chat_id, path);
    }

    public void addToExistingCache(Long chat_id, String path) {
        cache.put(chat_id, cache.get(chat_id) + path);
    }

    public boolean checkIfExist(Long chat_id) {
        return cache.containsKey(chat_id);
    }

    public String getFromCache(Long chat_id) {
        return cache.get(chat_id);
    }

    public void clearCache() {
        cache.clear();
    }
}
