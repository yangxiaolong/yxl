package com.yxl.controller;

import com.yxl.service.JoinService;
import com.yxl.service.order.Order;
import com.yxl.service.order.OrderRepository;
import com.yxl.vo.OrderDetailVO;
import com.yxl.vo.OrderVO;
import com.yxl.vo.v6.OrderDetailVOV6;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @auther yangxiaolong
 * @create 2024/9/13
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderDetailController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JoinService joinService;

    @GetMapping("/detail")
    public List<? extends OrderDetailVO> getByUserId(Long userId) {
        List<Order> orders = this.orderRepository.getByUserId(userId);

        List<OrderDetailVOV6> orderDetailVOS = orders.stream()
                .map(order -> new OrderDetailVOV6(OrderVO.apply(order)))
                .collect(toList());

        this.joinService.joinInMemory(OrderDetailVOV6.class, orderDetailVOS);
        return orderDetailVOS;
    }

}
