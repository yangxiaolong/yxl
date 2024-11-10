package com.lego.yxl.command.demo.command;

import com.lego.yxl.command.core.CommandForSync;
import lombok.Data;


@Data
public class SyncOrderByIdCommand
        extends CreateOrderCommand
        implements CommandForSync<Long> {
    private Long orderId;
    public SyncOrderByIdCommand(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public Long getKey() {
        return orderId;
    }
}
