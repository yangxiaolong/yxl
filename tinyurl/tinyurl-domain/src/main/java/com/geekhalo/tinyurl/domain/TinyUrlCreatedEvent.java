package com.geekhalo.tinyurl.domain;

public class TinyUrlCreatedEvent extends AbstractTinyUrlEvent{
    public TinyUrlCreatedEvent(TinyUrl source) {
        super(source);
    }
}
