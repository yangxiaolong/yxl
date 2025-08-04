package com.lego.yxl.core.command.support;

import java.util.function.Consumer;

public interface AggRoot<ID> {
    ID getId();
    void consumeAndClearEvent(Consumer<DomainEvent> eventConsumer);

    default void validate(){

    }
}
