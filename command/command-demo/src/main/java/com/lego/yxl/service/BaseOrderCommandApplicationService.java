package com.lego.yxl.service;

import com.google.common.collect.Lists;
import com.lego.yxl.command.*;
import com.lego.yxl.query.OrderStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
@Service
@Transactional
public class BaseOrderCommandApplicationService {

    @Autowired
    private EventListeners eventListeners;

    @Autowired
    private OrderRepositoryForCommand orderRepositoryForCommand;

    @Autowired
    private OrderCommandApplicationServiceImpl orderCommandService;

    public void create() {
        CreateOrderCommand command = getCreateOrderCommand();

        Long orderId = this.orderCommandService.create(command).getId();

        Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getAddress());
        Assertions.assertNotNull(order.getItems());
        Assertions.assertEquals(command.getProducts().size(), order.getItems().size());


        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertFalse(CollectionUtils.isEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);
    }

    public void sync() {
        {
            SyncOrderByIdCommand command = getSyncOrderCommand(2L);

            Long orderId = this.orderCommandService.syncByOrderId(command).getId();

            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

            Assertions.assertNotNull(order);
            Assertions.assertNotNull(order.getAddress());
            Assertions.assertNotNull(order.getItems());
            Assertions.assertEquals(command.getProducts().size(), order.getItems().size());
            Assertions.assertEquals(OrderStatus.SYNC, order.getStatus());


            List<Object> events = this.eventListeners.getEvents();
            Assertions.assertFalse(CollectionUtils.isEmpty(events));
            Object event = this.eventListeners.getEvents().get(0);
            Assertions.assertTrue(event instanceof OrderSyncEvent);
        }
        {
            CreateOrderCommand createCommand = getCreateOrderCommand();
            Long orderId = this.orderCommandService.create(createCommand).getId();

            this.eventListeners.clear();

            SyncOrderByIdCommand command = getSyncOrderCommand(orderId);

            Long id = this.orderCommandService.syncByOrderId(command).getId();

            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

            Assertions.assertNotNull(order);
            Assertions.assertNotNull(order.getAddress());
            Assertions.assertNotNull(order.getItems());
            Assertions.assertEquals(command.getProducts().size(), order.getItems().size());
            Assertions.assertEquals(OrderStatus.SYNC, order.getStatus());


            List<Object> events = this.eventListeners.getEvents();
            Assertions.assertTrue(CollectionUtils.isEmpty(events));
        }
    }

    public void paySuccess() {
        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.orderCommandService.create(command).getId();

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        PayByIdSuccessCommand paySuccessCommand = new PayByIdSuccessCommand();
        paySuccessCommand.setOrderId(orderId);
        paySuccessCommand.setChanel("微信");
        paySuccessCommand.setPrice(48L);
        this.orderCommandService.paySuccess(paySuccessCommand);

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
            List<PayRecord> payRecords = order.getPayRecords();
            Assertions.assertNotNull(payRecords);
            Assertions.assertFalse(CollectionUtils.isEmpty(payRecords));
            PayRecord payRecord = payRecords.get(0);
            Assertions.assertEquals("微信", payRecord.getChannel());
            Assertions.assertEquals(48L, payRecord.getPrice());
        }

        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertFalse(CollectionUtils.isEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);
    }

    public void cancel() {
        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.orderCommandService.create(command).getId();

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        this.orderCommandService.cancel(orderId);

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());

        }
    }

    private CreateOrderCommand getCreateOrderCommand() {
        CreateOrderCommand command = new CreateOrderCommand();
        command.setUserId(100L);
        command.setUserAddress(10000L);
        List<ProductForBuy> products = Lists.newArrayList();
        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1000L);
            product.setAmount(1);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1100L);
            product.setAmount(2);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1200L);
            product.setAmount(3);
            products.add(product);
        }

        command.setProducts(products);
        return command;
    }

    private SyncOrderByIdCommand getSyncOrderCommand(Long orderId) {
        SyncOrderByIdCommand command = new SyncOrderByIdCommand(orderId);
        command.setUserId(100L);
        command.setUserAddress(10000L);
        List<ProductForBuy> products = Lists.newArrayList();
        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1000L);
            product.setAmount(1);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1100L);
            product.setAmount(2);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1200L);
            product.setAmount(3);
            products.add(product);
        }

        command.setProducts(products);
        return command;
    }


}
