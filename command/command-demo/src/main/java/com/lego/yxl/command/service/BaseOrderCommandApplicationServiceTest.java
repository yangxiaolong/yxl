package com.lego.yxl.command.service;

import com.lego.yxl.command.command.*;
import com.lego.yxl.command.query.OrderStatus;
import jakarta.transaction.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lego.yxl.command.controller.CommandCreateUtil.getCreateOrderCommand;
import static com.lego.yxl.command.controller.CommandCreateUtil.getSyncOrderCommand;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
@Service
@Transactional
public abstract class BaseOrderCommandApplicationServiceTest {

    @Autowired
    private EventListeners eventListeners;

    @Autowired
    private OrderRepositoryForCommand orderRepositoryForCommand;

    public abstract OrderCommandApplicationService orderCommandService();

    public void setUp() {
        eventListeners.clear();
    }

    public void create() {
        CreateOrderCommand command = getCreateOrderCommand();

        Long orderId = this.orderCommandService().create(command).getId();

        Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getAddress());
        Assertions.assertNotNull(order.getItems());
        Assertions.assertEquals(command.getProducts().size(), order.getItems().size());


        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);
    }

    public void sync() {
        {
            SyncOrderByIdCommand command = getSyncOrderCommand(2L);

            Long orderId = this.orderCommandService().syncByOrderId(command).getId();

            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

            Assertions.assertNotNull(order);
            Assertions.assertNotNull(order.getAddress());
            Assertions.assertNotNull(order.getItems());
            Assertions.assertEquals(command.getProducts().size(), order.getItems().size());
            Assertions.assertEquals(OrderStatus.SYNC, order.getStatus());


            List<Object> events = this.eventListeners.getEvents();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
            Object event = this.eventListeners.getEvents().get(0);
            Assertions.assertTrue(event instanceof OrderCreatedEvent || event instanceof OrderSyncEvent);
        }
        {
            CreateOrderCommand createCommand = getCreateOrderCommand();
            Long orderId = this.orderCommandService().create(createCommand).getId();

            this.eventListeners.clear();

            SyncOrderByIdCommand command = getSyncOrderCommand(orderId);

            this.orderCommandService().syncByOrderId(command).getId();

            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);

            Assertions.assertNotNull(order);
            Assertions.assertNotNull(order.getAddress());
            Assertions.assertNotNull(order.getItems());
            Assertions.assertEquals(command.getProducts().size(), order.getItems().size());
            Assertions.assertEquals(OrderStatus.SYNC, order.getStatus());


            List<Object> events = this.eventListeners.getEvents();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
        }
    }

    public void paySuccess() {
        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.orderCommandService().create(command).getId();

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        PayByIdSuccessCommand paySuccessCommand = new PayByIdSuccessCommand();
        paySuccessCommand.setOrderId(orderId);
        paySuccessCommand.setChanel("微信");
        paySuccessCommand.setPrice(48L);
        this.orderCommandService().paySuccess(paySuccessCommand);

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.PAID, order.getStatus());
            List<PayRecord> payRecords = order.getPayRecords();
            Assertions.assertNotNull(payRecords);
            Assertions.assertTrue(CollectionUtils.isNotEmpty(payRecords));
            PayRecord payRecord = payRecords.get(0);
            Assertions.assertEquals("微信", payRecord.getChannel());
            Assertions.assertEquals(48L, payRecord.getPrice());
        }

        List<Object> events = this.eventListeners.getEvents();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(events));
        Object event = this.eventListeners.getEvents().get(0);
        Assertions.assertTrue(event instanceof OrderCreatedEvent);
        Object event1 = this.eventListeners.getEvents().get(1);
        Assertions.assertTrue(event1 instanceof OrderPaySuccessEvent);
    }

    public void cancel() {
        CreateOrderCommand command = getCreateOrderCommand();
        Long orderId = this.orderCommandService().create(command).getId();

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.NONE, order.getStatus());
        }

        this.orderCommandService().cancel(orderId);

        {
            Order order = this.orderRepositoryForCommand.findById(orderId).orElse(null);
            Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());

        }
    }

}
