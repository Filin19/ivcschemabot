package com.railway.ivc.ivcschemabot.handler;

/**
 * Interface to distributing message from bot Update object.
 * <p>
 * @param <T> message type.
 * <p>
 * @author Viktor Zaitsev.
 */
public interface Handler<T> {

    /**
     * Method to distribute message from bot Update object.
     * <p>
     * @param t generic type of bot message.
     */
    void distributeMessage(T t);
}
