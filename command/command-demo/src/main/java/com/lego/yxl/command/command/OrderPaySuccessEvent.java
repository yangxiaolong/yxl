package com.lego.yxl.command.command;

import com.lego.yxl.command.core.support.AbstractDomainEvent;
import lombok.Value;

@Value
public class OrderPaySuccessEvent
        extends AbstractDomainEvent<Long, Order>
        implements OrderEvent{
    public OrderPaySuccessEvent(Order order){
        super(order);
    }
}
