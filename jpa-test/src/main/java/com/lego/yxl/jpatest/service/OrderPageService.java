package com.lego.yxl.jpatest.service;

import com.lego.yxl.jpatest.enums.OrderStatus;
import com.lego.yxl.jpatest.repository.Order;
import com.lego.yxl.jpatest.repository.OrderCommandRepository;
import com.lego.yxl.jpatest.repository.OrderPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2025/5/1
 */
@Component
public class OrderPageService {

    @Autowired
    private OrderPageRepository repository;

    @Autowired
    OrderCommandRepository commandRepository;

    public Page<Order> getUsersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public Iterable<Order> findAll() {
        Sort.TypedSort<Order> sort = Sort.sort(Order.class);
        Sort orderIdDesc = sort.by(Order::getId).descending();
        Sort address = sort.by(Order::getAddress).ascending();
        return repository.findAll(address.and(orderIdDesc));
    }

    public List<Order> findTop10ByStatusOrderByIdDesc() {
        return commandRepository.findTop10ByStatusOrderByIdDesc(OrderStatus.NONE);
    }

}
