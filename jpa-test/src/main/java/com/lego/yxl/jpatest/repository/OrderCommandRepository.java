package com.lego.yxl.jpatest.repository;

import com.lego.yxl.jpatest.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
public interface OrderCommandRepository extends JpaRepository<Order, Long> {

    List<Order> findTop10ByStatusOrderByIdDesc(OrderStatus status);

}
