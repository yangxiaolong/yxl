package com.lego.jpa.test.event.controller;

import com.lego.jpa.test.repository.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
@Component
public class MyEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishEvent(String message) {
        MyEvent event = new MyEvent(message);
        System.out.println("publishEvent== " + Thread.currentThread().getName());
        eventPublisher.publishEvent(event);
    }

    public void publishEvent(EventController controller, Optional<Order> optional) {
        MyTransactionalEvent myTransactionalEvent = new MyTransactionalEvent(controller, optional);
        eventPublisher.publishEvent(myTransactionalEvent);
    }

}