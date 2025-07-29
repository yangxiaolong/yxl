package com.lego.yxl.wide.core;

import com.lego.yxl.wide.WideItemDataProvider;
import com.lego.yxl.wide.jpa.Order;
import com.lego.yxl.wide.jpa.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@org.springframework.core.annotation.Order(value = Ordered.HIGHEST_PRECEDENCE)
public class OrderProvider implements WideItemDataProvider<WideOrderType, Long, Order> {
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> apply(List<Long> key) {
        return orderDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.ORDER;
    }
}
