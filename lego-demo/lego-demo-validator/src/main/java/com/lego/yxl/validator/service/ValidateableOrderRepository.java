package com.lego.yxl.validator.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidateableOrderRepository extends JpaRepository<ValidateableOrder, Long> {
}
