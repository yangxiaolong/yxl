package com.lego.yxl.jpatest.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
public class ValidStudentCountConstraintValidator implements ConstraintValidator<ValidStudentCount, Room> {

    @Override
    public boolean isValid(Room room, ConstraintValidatorContext context) {
        if (room == null) {
            return true;
        }
        boolean isValid = room.getStudentNames().size() <= room.getMaxStuNum();

        // 自定义提示语（当然你也可以不自定义，那就使用注解里的message字段的值）
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("校验失败xxx")
                    .addPropertyNode("studentNames")
                    .addConstraintViolation();
        }
        return isValid;
    }

}
