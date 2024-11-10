package com.lego.yxl.validator.service;

import com.lego.yxl.validator.core.FixTypeBusinessValidator;
import com.lego.yxl.validator.core.common.ValidateErrorHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class UserStatusValidator extends FixTypeBusinessValidator<CreateOrderContext> {

    @Override
    public void validate(CreateOrderContext context, ValidateErrorHandler validateErrorHandler) {
        if (context.getUser() == null){
            validateErrorHandler.handleError("user", "1", "用户不存在");
        }
        if (!context.getUser().isEnable()){
            validateErrorHandler.handleError("user", "2", "当前用户不可以");
        }
    }
}
