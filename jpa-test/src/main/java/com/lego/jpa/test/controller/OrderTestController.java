package com.lego.jpa.test.controller;

import com.google.common.collect.Lists;
import com.lego.jpa.test.command.CreateOrderCommand;
import com.lego.jpa.test.command.PaySuccessCommand;
import com.lego.jpa.test.command.ProductForBuy;
import com.lego.jpa.test.repository.Order;
import com.lego.jpa.test.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderTestController {

    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public void create() {
        // 创订单，将整个 Order 聚合根全部保存到数据库，包括
        // 1. order
        // 2. orderItem
        // 3. orderAddress
        CreateOrderCommand command = createOrderCommand(10L);
        command.setUserAddress("userAddress");

        Order order = this.orderService.createOrder(command);
        Assertions.assertNotNull(order.getId());
    }

    @GetMapping("/modifyAddress/{orderId}/{address}")
    public void modifyAddress(@PathVariable Long orderId, @PathVariable String address) {
        orderService.modifyAddress(orderId, address);
    }

    @GetMapping("/modifyAddress_add")
    public void modifyAddress_add() {
        // 新订单不存储地址信息（没有 userAddress）
        Order order;
        {
            CreateOrderCommand command = createOrderCommand(20L);
            // 将收获地址设置为 null
            command.setUserAddress(null);
            order = this.orderService.createOrder(command);
            Assertions.assertNotNull(order.getId());
        }

        // 修改时，直接创建地址（插入新数据）
        String address = "新增地址";
        // Lazy 加载，只加载 orderAddress
        // 修改后，只更新 OrderAddress
        this.orderService.modifyAddress(order.getId(), address);
    }

    @GetMapping("/modifyAddress_update")
    public void modifyAddress_update() {
        // 新订单部存在地址信息（没有 userAddress）
        Order order = null;
        {
            CreateOrderCommand command = createOrderCommand(30L);
            order = this.orderService.createOrder(command);
            Assertions.assertNotNull(order.getId());
        }

        // Lazy 加载，只加载 orderAddress
        // 修改后，只更新 OrderAddress
        String address = "修改地址";
        this.orderService.modifyAddress(order.getId(), address);
    }

    @GetMapping("/paySuccess")
    public void paySuccess() {
        Order order = null;
        {
            CreateOrderCommand command = createOrderCommand(50L);
            order = this.orderService.createOrder(command);
            Assertions.assertNotNull(order.getId());
        }

        PaySuccessCommand paySuccessCommand = new PaySuccessCommand();
        paySuccessCommand.setOrderId(order.getId());
        paySuccessCommand.setPrice(1000L);
        paySuccessCommand.setChanel("微信支付");
        // Lazy 加载，只加载 orderItem
        // 修改后，更新 order 和 OrderItem
        this.orderService.paySuccess(paySuccessCommand);
    }

    private CreateOrderCommand createOrderCommand(long userId) {
        CreateOrderCommand command = new CreateOrderCommand().setUserId(userId);

        ProductForBuy aa = new ProductForBuy().setProductId(1L).setAmount(1).setProductName("aa").setPrice(4);
        ProductForBuy bb = new ProductForBuy().setProductId(2L).setAmount(2).setProductName("bb").setPrice(3);
        command.setProducts(Lists.newArrayList(aa, bb));

        return command;
    }

}
