package com.lego.yxl.command.command;

import com.lego.yxl.command.core.support.AbstractDomainEvent;
import lombok.Value;

@Value
public class OrderCreatedEvent
        extends AbstractDomainEvent<Long, Order>
        implements OrderEvent{
    public OrderCreatedEvent(Order order){
        super(order);
    }
}
