package com.lego.yxl.jpatest.service;

import com.lego.yxl.jpatest.command.CreateOrderCommand;
import com.lego.yxl.jpatest.command.PaySuccessCommand;
import com.lego.yxl.jpatest.enums.OrderStatus;
import com.lego.yxl.jpatest.repository.Order;
import com.lego.yxl.jpatest.repository.OrderCommandRepository;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
@Component
@Transactional
public class OrderService {

    @Autowired
    OrderCommandRepository repository;

    public Order createOrder(CreateOrderCommand command) {
        // 创建内存对象
        Order order = Order.create(command);
        // 保存到数据库
        this.repository.save(order);
        return order;
    }

    public void modifyAddress(Long orderId, String address) {
        Optional<Order> orderOptional = repository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.modifyAddress(address);
            this.repository.save(order);

            Order orderInDB = this.repository.findById(order.getId()).get();
            Assertions.assertEquals(address, orderInDB.getAddress().getDetail());
        }
    }

    public void paySuccess(PaySuccessCommand command) {
        Optional<Order> orderOptional = repository.findById(command.getOrderId());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.paySuccess(command);
            this.repository.save(order);

            Order orderInDB = this.repository.findById(order.getId()).get();
            Assertions.assertEquals(OrderStatus.PAID, orderInDB.getStatus());
            orderInDB.getItems().forEach(orderItem -> {
                Assertions.assertEquals(OrderStatus.PAID, orderItem.getStatus());
            });
        }
    }

}
