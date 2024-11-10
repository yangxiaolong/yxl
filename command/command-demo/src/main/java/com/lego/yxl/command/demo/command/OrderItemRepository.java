package com.lego.yxl.command.demo.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderItemRepositoryForCommand")
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
