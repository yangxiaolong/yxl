package com.lego.jpa.test.command;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
@Data
@Accessors(chain = true)
public class PaySuccessCommand {

    private Long orderId;

    private Long price;

    private String chanel;

}
