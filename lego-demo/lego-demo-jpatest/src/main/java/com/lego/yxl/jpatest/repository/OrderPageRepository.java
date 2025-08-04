package com.lego.yxl.jpatest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @auther yangxiaolong
 * @create 2025/5/1
 */
public interface OrderPageRepository extends PagingAndSortingRepository<Order, Long> {

}