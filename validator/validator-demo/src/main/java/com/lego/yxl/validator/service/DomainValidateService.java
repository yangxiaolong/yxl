package com.lego.yxl.validator.service;

import com.lego.yxl.loader.context.CreateOrderCmd;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DomainValidateService {
    void createOrder(@NotNull CreateOrderContext context);

    void createOrder(@NotNull CreateOrderCmd cmd);
}
