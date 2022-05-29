package com.railway.ivc.ivcschemabot.cache;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class to clear Cache every day at midnight.
 * <p>
 * @author Viktor Zaitsev
 */
@Component
public class CacheCleaner {

    /**
     * Initial Logger Log4j for class CacheCleaner.
     */
    private static final Logger LOG = LogManager.getLogger(CacheCleaner.class);

    /**
     * Object MessageCache;
     */
    private final MessageCache messageCache;

    /**
     * Constructor for class CacheCleaner.
     * <p>
     * @param messageCache MessageCache object.
     */
    public CacheCleaner(MessageCache messageCache) {
        this.messageCache = messageCache;
    }

    /**
     * Method to clean cache every day at midnight
     */
    @Scheduled(cron = "0 00 00 * * *")
    public void clearCacheInMidnight() {
        LOG.info("cache cleaned in: " + LocalDateTime.now());
        messageCache.clearCache();
    }
}
