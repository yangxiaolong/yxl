package com.lego.yxl.support.handler.aggfactory;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Constructor;

@Slf4j
public class ConstructorBasedSmartAggFactory extends AbstractSmartAggFactory{
    private final Constructor constructor;
    public ConstructorBasedSmartAggFactory(Class contextClass, Class aggClass, Constructor constructor) {
        super(contextClass, aggClass);
        Preconditions.checkArgument(constructor != null);
        this.constructor = constructor;
    }

    @Override
    public Object create(Object command) {
        try {
            return ConstructorUtils.invokeConstructor(aggClass, command);
        } catch (Exception e) {
            log.error("failed to invoke Constructor {} use {}", aggClass, command);
            throw new RuntimeException(e);
        }
    }
}
