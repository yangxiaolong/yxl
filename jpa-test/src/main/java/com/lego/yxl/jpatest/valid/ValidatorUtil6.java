package com.lego.yxl.jpatest.valid;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
public class ValidatorUtil6 {

    @Test
    public void test1() {
        Room room = new Room();
        room.name = "YourBatman";
        Result<Room> result = new Result<>();
        result.setData(room);

        // 把Result作为属性放进去
        ResultDemo resultDemo = new ResultDemo();
        resultDemo.roomResult = result;

        // 注册自定义的值提取器
        Validator validator = ValidatorUtil.obtainValidatorFactory()
                .usingContext()
                .addValueExtractor(new ResultValueExtractor())
                .getValidator();

        ValidatorUtil.printViolations(validator.validate(resultDemo));
    }

    @Test
    public void test2() {
        Room room = new Room();
        room.setStudentNames(Collections.singletonList("YourBatman"));

        ValidatorUtil.printViolations(ValidatorUtil.obtainValidator().validate(room));
    }

}
