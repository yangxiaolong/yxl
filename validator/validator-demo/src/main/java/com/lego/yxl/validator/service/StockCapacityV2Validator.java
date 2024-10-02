package com.lego.yxl.validator.service;

import com.lego.yxl.core.FixTypeBusinessValidator;
import com.lego.yxl.core.common.ValidateErrorHandler;
import com.lego.yxl.context.CreateOrderContextV2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(3)
public class StockCapacityV2Validator
        extends FixTypeBusinessValidator<CreateOrderContextV2> {

    @Override
    public void validate(CreateOrderContextV2 context, ValidateErrorHandler validateErrorHandler) {
        if (context.getStock() == null){
            validateErrorHandler.handleError("stock", "3", "库存不存在");
        }
        if (context.getStock().getCount() < context.getCmd().getCount()){
            validateErrorHandler.handleError("stock", "4", "库存不足");
        }
    }
}
