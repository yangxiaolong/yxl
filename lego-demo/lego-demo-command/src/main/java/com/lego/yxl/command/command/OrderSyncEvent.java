package com.lego.yxl.command.command;

import com.lego.yxl.core.command.support.AbstractDomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderSyncEvent extends AbstractDomainEvent<Long, Order> implements OrderEvent {
    public OrderSyncEvent(Order order) {
        super(order);
    }
}
