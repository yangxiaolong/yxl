package com.lego.yxl.validator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateableOrderApplicationService {
    @Autowired
    private ValidateableOrderRepository repository;

    public void createOrder(ValidateableOrder order){
        this.repository.save(order);
    }
}
