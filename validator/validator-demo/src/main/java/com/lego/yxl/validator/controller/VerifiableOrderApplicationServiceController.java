package com.lego.yxl.validator.controller;

import com.lego.yxl.core.ValidateException;
import com.lego.yxl.validator.service.ValidateableOrder;
import com.lego.yxl.validator.service.ValidateableOrderApplicationService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validator/verify")
public class VerifiableOrderApplicationServiceController {

    @Autowired
    private ValidateableOrderApplicationService applicationService;

    @RequestMapping("/createOrder_error")
    public void createOrder_error() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                ValidateableOrder order = new ValidateableOrder();
                order.setSellPrice(20);
                order.setAmount(2);
                order.setDiscountPrice(5);
                order.setManualPrice(1);

                order.setPayPrice(35);
                this.applicationService.createOrder(order);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrder")
    public void createOrder() {
        ValidateableOrder order = new ValidateableOrder();
        order.setSellPrice(20);
        order.setAmount(2);
        order.setDiscountPrice(5);
        order.setManualPrice(1);

        order.setPayPrice(34);
        this.applicationService.createOrder(order);
    }

}
