package com.lego.yxl.jpatest.controller;

import com.lego.yxl.jpatest.repository.Order;
import com.lego.yxl.jpatest.service.OrderPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2025/5/1
 */
@RestController
@RequestMapping("/order/page")
@Slf4j
public class OrderPageController {

    @Autowired
    OrderPageService orderService;

    @GetMapping("/users")
    public Page<Order> getUsersByPage() {
        return orderService.getUsersByPage(0, 10);
    }

    @GetMapping("/users/all")
    public Iterable<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/users/findTop10ByStatusOrderByIdDesc")
    public List<Order> findTop10ByStatusOrderByIdDesc() {
        return orderService.findTop10ByStatusOrderByIdDesc();
    }
}
