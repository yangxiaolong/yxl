package com.lego.yxl.command.command;

import com.lego.yxl.agg.DomainEvent;

public interface OrderEvent
        extends DomainEvent<Long, Order> {
}
