package com.lego.yxl.jpatest.event.controller;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
@Component
public class MyEventListener {

    @Bean
    public ExecutorService eventListenerExecutor() {
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("event-Thread-%d")
                .daemon(true)
                .build();
        int maxSize = Runtime.getRuntime().availableProcessors() * 3;
        return new ThreadPoolExecutor(1, maxSize,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @EventListener
    public void onApplicationEvent(MyEvent event) {
        // 处理事件
        System.out.println("onApplicationEvent== " + Thread.currentThread().getName());
        System.out.println("Received event: " + event.getMessage());
    }

    @EventListener
    @Async(value = "eventListenerExecutor")
    public void onApplicationEventAsync(MyEvent event) {
        // 处理事件
        System.out.println("onApplicationEventAsync== " + Thread.currentThread().getName());
        System.out.println("Received event async: " + event.getMessage());
    }

    @TransactionalEventListener
    public void handleMyTransactionalEvent(MyTransactionalEvent event) {
        // 处理 MyTransactionalEvent
        System.out.println("Received MyTransactionalEvent: " + event);
    }

    @TransactionalEventListener
    @Async(value = "eventListenerExecutor")
    public void handleMyTransactionalEventAsync(MyTransactionalEvent event) {
        // 处理 MyTransactionalEvent
        System.out.println("Received MyTransactionalEvent Async: " + event);
    }

}