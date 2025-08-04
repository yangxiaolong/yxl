package com.lego.yxl.joininmemory.controller;

import com.lego.yxl.core.joininmemory.service.JoinService;
import com.lego.yxl.joininmemory.repository.Order;
import com.lego.yxl.joininmemory.repository.OrderRepository;
import com.lego.yxl.joininmemory.vo.OrderDetailVO;
import com.lego.yxl.joininmemory.vo.OrderVO;
import com.lego.yxl.joininmemory.vo.OrderDetailVOV6;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
