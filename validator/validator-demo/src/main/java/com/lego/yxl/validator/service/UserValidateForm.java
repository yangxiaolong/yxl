package com.lego.yxl.validator.service;

import com.lego.yxl.validator.core.common.ValidateErrorHandler;
import com.lego.yxl.validator.core.common.Verifiable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserValidateForm implements Verifiable {
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Override
    public void validate(ValidateErrorHandler validateErrorHandler) {
        if (getName().equals(getPassword())) {
            validateErrorHandler.handleError("user", "1", "用户名密码不能相同");
        }
    }
}
