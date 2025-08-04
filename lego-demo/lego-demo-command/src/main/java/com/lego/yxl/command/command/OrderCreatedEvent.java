package com.lego.yxl.command.command;

import com.lego.yxl.core.command.support.AbstractDomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderCreatedEvent extends AbstractDomainEvent<Long, Order> implements OrderEvent {
    public OrderCreatedEvent(Order order) {
        super(order);
    }
}
