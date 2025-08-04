package com.lego.yxl.joininmemory.repository;

import com.google.common.collect.Lists;
import com.lego.yxl.core.util.TimeUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    public List<Order> getByUserId(Long userId) {
        TimeUtils.sleepAsMS(5);
        List<Order> orders = Lists.newArrayListWithCapacity(100);
        for (int i = 0; i < 100; i++) {
            orders.add(createOrder(i + 1));
        }
        return orders;
    }

    public List<Order> getById(List<Long> ids) {
        return ids.stream()
                .map(this::createOrderById)
                .collect(Collectors.toList());
    }

    private Order createOrder(long userId) {
        return Order.builder()
                .id(RandomUtils.nextLong(0, 1000000))
                .userId(userId)
                .addressId(userId + 1)
                .productId(userId + 2)
                .build();
    }

    private Order createOrderById(long id) {
        long userId = RandomUtils.nextLong(0, 10);
        return Order.builder()
                .id(id)
                .userId(userId)
                .addressId(userId + 1)
                .productId(userId + 2)
                .build();
    }
}
