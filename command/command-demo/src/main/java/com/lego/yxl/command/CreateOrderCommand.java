package com.lego.yxl.command;

import com.lego.yxl.CommandForCreate;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderCommand implements CommandForCreate {
    private Long userId;
    private Long userAddress;
    private List<ProductForBuy> products;
}
