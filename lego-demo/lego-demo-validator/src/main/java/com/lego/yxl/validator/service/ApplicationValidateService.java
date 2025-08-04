package com.lego.yxl.validator.service;

import com.lego.yxl.validator.service.pwd.Password;
import com.lego.yxl.validator.service.pwd.PasswordConsistency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;



@Validated
public interface ApplicationValidateService {
    /**
     * 简单参数
     * @param id
     */
    void singleValidate(@NotNull(message = "id 不能为null") Long id);

    /**
     * 1. 简单参数
     * 2. Bean 中的简单属性
     * @param singleForm
     */
    void singleValidate(@Valid @NotNull(message = "form 不能为 null") SingleForm singleForm);

    void customSingleValidate(@Valid @PasswordConsistency(message = "两次密码不相同") @NotNull Password password);

    void validateForm(@NotNull @Valid UserValidateForm userValidateForm);
}
