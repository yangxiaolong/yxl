package com.lego.jpa.test.event.controller;

import com.lego.jpa.test.repository.Order;
import com.lego.jpa.test.repository.OrderCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class EventController {

    @Autowired
    MyEventPublisher myEventPublisher;

    @Autowired
    OrderCommandRepository repository;

    @GetMapping("/publishEvent")
    public void publishEvent() {
        myEventPublisher.publishEvent("hello world!");
    }

    @GetMapping("/myTransactionalEvent")
    @Transactional
    public void myTransactionalEvent() {
        // 做一些事情
        Optional<Order> optional = repository.findById(20L);

        // 发布事件
        myEventPublisher.publishEvent("12121");
        // 发布事务事件
        myEventPublisher.publishEvent(this, optional);
    }

}
