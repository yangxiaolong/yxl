package com.lego.yxl.command.demo.command;

import com.lego.yxl.command.core.AggNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomOrderCommandServiceImpl implements CustomOrderCommandService {
    @Autowired
    private OrderRepositoryForCommand orderRepositoryForCommand;

    @Override
    public void cancel(Long orderId) {
        Order order = this.orderRepositoryForCommand.findById(orderId).orElseThrow(() -> new AggNotFoundException(orderId));
        order.cancel();
        this.orderRepositoryForCommand.save(order);
    }

}
