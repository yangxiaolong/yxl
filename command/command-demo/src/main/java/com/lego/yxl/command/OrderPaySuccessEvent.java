package com.lego.yxl.command;

import com.lego.yxl.support.AbstractDomainEvent;
import lombok.Value;

@Value
public class OrderPaySuccessEvent
        extends AbstractDomainEvent<Long, Order>
        implements OrderEvent{
    public OrderPaySuccessEvent(Order order){
        super(order);
    }
}
