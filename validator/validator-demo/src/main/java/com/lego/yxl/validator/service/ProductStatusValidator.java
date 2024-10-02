package com.lego.yxl.validator.service;

import com.lego.yxl.core.FixTypeBusinessValidator;
import com.lego.yxl.core.common.ValidateErrorHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(2)
public class ProductStatusValidator extends FixTypeBusinessValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if (context.getProduct() == null) {
            validateErrorHandler.handleError("product", "2", "商品不存在");
        }
        if (!context.getProduct().isSaleable()) {
            validateErrorHandler.handleError("product", "3", "商品不可售卖");
        }
    }
}
