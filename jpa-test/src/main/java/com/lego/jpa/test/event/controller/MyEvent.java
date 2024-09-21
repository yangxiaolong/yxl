package com.lego.jpa.test.event.controller;

/**
 * @auther yangxiaolong
 * @create 2024/9/21
 */
public class MyEvent {
    private String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}