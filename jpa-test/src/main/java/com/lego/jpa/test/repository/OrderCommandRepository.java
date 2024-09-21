package com.lego.jpa.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @auther yangxiaolong
 * @create 2024/9/20
 */
public interface OrderCommandRepository extends JpaRepository<Order, Long> {

}
