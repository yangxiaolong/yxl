package com.lego.yxl.command.demo.command;

import com.lego.yxl.command.core.CommandForUpdateById;
import lombok.Data;

@Data
public class PayByIdSuccessCommand implements CommandForUpdateById {
    private Long orderId;

    private String chanel;

    private Long price;

    @Override
    public Object getId() {
        return orderId;
    }
}
