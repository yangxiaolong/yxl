package com.lego.yxl.command.command;

import com.lego.yxl.command.core.CommandWithKeyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepositoryForCommand extends JpaRepository<Order, Long>,
        CommandWithKeyRepository<Order, Long, Long> {

    @Override
    default Order sync(Order entity) {
        return save(entity);
    }

    @Override
    default Optional<Order> findByKey(Long key) {
        return findById(key);
    }

}
