package com.lego.yxl.command.controller;

import com.lego.yxl.command.command.OrderCommandApplicationService;
import com.lego.yxl.command.command.OrderCommandApplicationServiceImpl;
import com.lego.yxl.command.service.BaseOrderCommandApplicationServiceTest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
@Slf4j
public class CommandController extends BaseOrderCommandApplicationServiceTest {

    @Resource
    private OrderCommandApplicationServiceImpl orderCommandService;

    @Override
    public OrderCommandApplicationService orderCommandService() {
        return orderCommandService;
    }

    @GetMapping("/create")
    public void create() {
        setUp();
        super.create();
    }

    @GetMapping("/sync")
    public void sync() {
        setUp();
        super.sync();
    }

    @GetMapping("/paySuccess")
    public void paySuccess() {
        setUp();
        super.paySuccess();
    }

    @GetMapping("/cancel")
    public void cancel() {
        setUp();
        super.cancel();
    }

}
