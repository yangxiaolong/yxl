package com.lego.yxl.controller;

import com.lego.yxl.service.BaseOrderCommandApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
@RestController
@RequestMapping("/command")
@Slf4j
public class CommandController {

    @Autowired
    BaseOrderCommandApplicationService baseOrderService;

    @GetMapping("/create")
    public void create() {
        baseOrderService.create();
    }

    @GetMapping("/sync")
    public void sync() {
        baseOrderService.sync();
    }

    @GetMapping("/paySuccess")
    public void paySuccess() {
        baseOrderService.paySuccess();
    }

    @GetMapping("/cancel")
    public void cancel() {
        baseOrderService.cancel();
    }

}
