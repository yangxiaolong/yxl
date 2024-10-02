package com.lego.yxl.validator.service;

import com.lego.yxl.core.FixTypeBusinessValidator;
import com.lego.yxl.core.common.ValidateErrorHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(3)
public class StockCapacityValidator extends FixTypeBusinessValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if (context.getStock() == null){
            validateErrorHandler.handleError("stock", "3", "库存不存在");
        }
        if (context.getStock().getCount() < context.getCount()){
            validateErrorHandler.handleError("stock", "4", "库存不足");
        }
    }
}
