package com.lego.yxl.vo;

import com.lego.yxl.repository.order.Order;
import lombok.Value;

/**
 */
@Value
public class OrderVO {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;

    public static OrderVO apply(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderVO(order.getId(), order.getUserId(), order.getAddressId(), order.getProductId());
    }
}
