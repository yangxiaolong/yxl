package com.lego.yxl.command;

import com.lego.yxl.support.AbstractDomainEvent;
import lombok.Value;

@Value
public class OrderSyncEvent
        extends AbstractDomainEvent<Long, Order>
        implements OrderEvent{
    public OrderSyncEvent(Order order){
        super(order);
    }
}
