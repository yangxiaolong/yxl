package com.lego.yxl.command.demo.command;

import com.lego.yxl.command.core.CommandForCreate;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderCommand implements CommandForCreate {
    private Long userId;
    private Long userAddress;
    private List<ProductForBuy> products;
}
