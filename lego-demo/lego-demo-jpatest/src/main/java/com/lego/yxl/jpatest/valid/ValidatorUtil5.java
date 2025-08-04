package com.lego.yxl.jpatest.valid;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
public class ValidatorUtil5 {

    @Test
    public void testField() {
        Room bean = new Room();
        bean.finished = false;
        ValidatorUtil.printViolations(ValidatorUtil.obtainValidator().validate(bean));
    }

    @Test
    public void testProperty() {
        RoomProperty bean = new RoomProperty();
        bean.finished = false;
        ValidatorUtil.printViolations(ValidatorUtil.obtainValidator().validate(bean));
    }

    //其实它是把List当作一个Bean，去验证List里面的标注有约束注解的属性/方法。很显然，List里面不可能标注有约束注解嘛，所以什么都不输出喽
    @Test
    public void testContainer() {
        List<@NotNull Room> rooms = new ArrayList<>();
        rooms.add(null);
        rooms.add(new Room());

        Room room = new Room();
        room.name = "YourBatman";
        rooms.add(room);

        ValidatorUtil.printViolations(ValidatorUtil.obtainValidator().validate(rooms));
    }

    public static void main(String[] args) {
        List<@NotNull Room> beans = new ArrayList<>();
        beans.add(null);
        beans.add(new Room());

        Room room = new Room();
        room.name = "YourBatman";
        beans.add(room);

        // 必须基于Java Bean，验证才会生效
        RoomList rooms = new RoomList(beans);
        ValidatorUtil.printViolations(ValidatorUtil.obtainValidator().validate(rooms));
    }

}
