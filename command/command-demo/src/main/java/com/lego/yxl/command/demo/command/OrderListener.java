package com.lego.yxl.command.demo.command;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;

public class OrderListener {
    @PrePersist
    public void preCreate(Order order) {
        order.checkBiz();
    }

    @PostPersist
    public void postCreate(Order order) {
        order.checkBiz();
    }
}
