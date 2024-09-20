package com.lego.yxl.command;

import com.lego.yxl.CommandForUpdateById;
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
