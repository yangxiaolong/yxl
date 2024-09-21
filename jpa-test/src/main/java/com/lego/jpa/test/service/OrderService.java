package com.lego.jpa.test.service;

import com.lego.jpa.test.command.CreateOrderCommand;
import com.lego.jpa.test.command.PaySuccessCommand;
import com.lego.jpa.test.enums.OrderStatus;
import com.lego.jpa.test.repository.Order;
import com.lego.jpa.test.repository.OrderCommandRepository;
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
