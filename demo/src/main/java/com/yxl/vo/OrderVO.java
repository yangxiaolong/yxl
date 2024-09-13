package com.yxl.vo;

import com.yxl.service.order.Order;
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
