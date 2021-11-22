package com.railwai.ivc.ivcschemabot.handler;

public interface Handler<T> {

    void distributeMessage(T t);
}
