package com.lego.yxl.validator.service.pwd;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Password {

    @NotEmpty(message = "密码不能为空")
    private String input1;

    @NotEmpty(message = "确认密码不能为空")
    private String input2;

}
