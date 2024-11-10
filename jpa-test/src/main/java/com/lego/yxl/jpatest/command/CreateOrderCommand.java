package com.lego.yxl.jpatest.command;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateOrderCommand {

    private Long userId;

    private String userAddress;

    private List<ProductForBuy> products;

}
