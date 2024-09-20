package com.lego.yxl.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCommandServiceController {
    @Autowired
    private CreateOrderCommandService createOrderCommandService;

}
