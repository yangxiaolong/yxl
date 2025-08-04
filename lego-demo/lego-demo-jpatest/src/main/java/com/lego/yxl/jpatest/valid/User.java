package com.lego.yxl.jpatest.valid;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorContext;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.metadata.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.engine.DefaultClockProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.hibernate.validator.internal.engine.ValidatorContextImpl;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @auther yangxiaolong
 * @create 2024/12/28
 */
@Data
public class User {

    @NotNull
    private String name;

    @Length(min = 20)
    @NotNull
    private String fullName;

    @Test
    public void test5() {
        User user = new User();
        user.setName("YourBatman");

        Set<ConstraintViolation<User>> result = ValidatorUtil.obtainValidator()
                .validate(user);
        ValidatorUtil.printViolations(result);
    }

    @Test
    public void test6() {
        User user = new User();
        user.setFullName("YourBatman");

        Set<ConstraintViolation<User>> result = ValidatorUtil.obtainValidator()
                .validateProperty(user, "fullName");
        ValidatorUtil.printViolations(result);
    }

    @Test
    public void test7() {
        Set<ConstraintViolation<User>> result = ValidatorUtil.obtainValidator()
                .validateValue(User.class, "fullName", "A哥");
        ValidatorUtil.printViolations(result);
    }

    @Test
    public void test8() {
        BeanDescriptor beanDescriptor = ValidatorUtil.obtainValidator().getConstraintsForClass(User.class);
        System.out.println("此类是否需要校验：" + beanDescriptor.isBeanConstrained());

        // 获取属性、方法、构造器的约束
        Set<PropertyDescriptor> constrainedProperties = beanDescriptor.getConstrainedProperties();
        Set<MethodDescriptor> constrainedMethods = beanDescriptor.getConstrainedMethods(MethodType.GETTER);
        Set<ConstructorDescriptor> constrainedConstructors = beanDescriptor.getConstrainedConstructors();
        System.out.println("需要校验的属性：" + constrainedProperties);
        System.out.println("需要校验的方法：" + constrainedMethods);
        System.out.println("需要校验的构造器：" + constrainedConstructors);

        PropertyDescriptor fullNameDesc = beanDescriptor.getConstraintsForProperty("fullName");
        System.out.println(fullNameDesc);
        System.out.println("fullName属性的约束注解个数：" + fullNameDesc.getConstraintDescriptors().size());
    }

    @Test
    public void test2() {
        ValidatorFactoryImpl validatorFactory = (ValidatorFactoryImpl) ValidatorUtil.obtainValidatorFactory();
        // 使用默认的Context上下文，并且初始化一个Validator实例
        // 必须传入一个校验器工厂实例哦
        ValidatorContext validatorContext = new ValidatorContextImpl(validatorFactory)
                .parameterNameProvider(new DefaultParameterNameProvider())
                .clockProvider(DefaultClockProvider.INSTANCE);

        // 通过该上下文，生成校验器实例（注意：调用多次，生成实例是多个哟）
        System.out.println(validatorContext.getValidator());
    }

    @Test
    public void test3() {
        Validator validator = ValidatorUtil.obtainValidatorFactory().usingContext()
                .parameterNameProvider(new DefaultParameterNameProvider())
                .clockProvider(DefaultClockProvider.INSTANCE)
                .getValidator();
        System.out.println(validator);
    }

}