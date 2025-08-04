package com.lego.yxl.validator.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SingleForm {
    @NotNull(message = "id不能为null")
    private Long id;

    @NotEmpty(message = "name不能为空")
    private String name;
}
