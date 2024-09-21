package com.lego.jpa.test.command;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductForBuy {

    private Long productId;
    private Integer amount;
    private String productName;
    private int price;

}
