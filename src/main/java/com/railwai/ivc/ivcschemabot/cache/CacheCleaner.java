package com.railwai.ivc.ivcschemabot.cache;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class to clear Cache every day at midnight.
 * <p>
 * @author Viktor Zaitsev
 */
@Component
public class CacheCleaner {

    /**
     * Object MessageCache;
     */
    private final MessageCache messageCache;

    /**
     * Constructor for class CacheCleaner.
     * @param messageCache MessageCache object.
     */
    public CacheCleaner(MessageCache messageCache) {
        this.messageCache = messageCache;
    }

    /**
     * Method to clean cache every day at midnight
     */
    //@Scheduled(cron = "0 00 00 * * *")
    @Scheduled(fixedRate = 10000)
    public void clearCacheInMidnight() {
        System.out.println("cache cleaned");
        messageCache.clearCache();
    }
}
