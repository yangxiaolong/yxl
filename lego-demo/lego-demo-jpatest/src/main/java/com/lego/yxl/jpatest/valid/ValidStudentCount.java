package com.lego.yxl.jpatest.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidStudentCountConstraintValidator.class})
public @interface ValidStudentCount {

    String message() default "学生人数超过最大限额";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}