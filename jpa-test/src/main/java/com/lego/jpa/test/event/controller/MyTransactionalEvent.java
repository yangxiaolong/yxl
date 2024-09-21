package com.lego.jpa.test.event.controller;

import com.lego.jpa.test.repository.Order;

import java.util.Optional;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
public class MyTransactionalEvent {

    private EventController controller;
    private Optional<Order> optional;

    public MyTransactionalEvent(EventController controller, Optional<Order> optional) {
        this.controller = controller;
        this.optional = optional;
    }
}
