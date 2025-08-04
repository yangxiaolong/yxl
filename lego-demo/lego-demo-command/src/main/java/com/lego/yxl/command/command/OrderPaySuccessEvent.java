package com.lego.yxl.command.command;

import com.lego.yxl.core.command.support.AbstractDomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class OrderPaySuccessEvent extends AbstractDomainEvent<Long, Order> implements OrderEvent {
    public OrderPaySuccessEvent(Order order) {
        super(order);
    }
}
