package com.lego.yxl.jpatest.valid;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

import java.util.Set;

/**
 * https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzI0MTUwOTgyOQ==&action=getalbum&album_id=1630480921197887488&scene=21#wechat_redirect
 *
 * @auther yangxiaolong
 * @create 2024/12/28
 */
public abstract class ValidatorUtil {

    public static ValidatorFactory obtainValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public static Validator obtainValidator() {
        return obtainValidatorFactory().getValidator();
    }

    public static ExecutableValidator obtainExecutableValidator() {
        return obtainValidator().forExecutables();
    }

    public static <T> void printViolations(Set<ConstraintViolation<T>> violations) {
        violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": "
                + v.getInvalidValue()).forEach(System.out::println);
    }

}
