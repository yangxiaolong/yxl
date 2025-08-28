package com.lego.yxl.jpatest.repository;

import com.lego.yxl.jpatest.enums.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
public interface OrderCommandRepository extends JpaRepository<Order, Long> {

    //N+1问题, 2. 使用实体图（Entity Graph，JPA 2.1+，推荐，更灵活）
    @EntityGraph(value = "Order.withItems")
    List<Order> findTop10ByStatusOrderByIdDesc(OrderStatus status);

}
