package com.lego.yxl.command.core.support.handler.contextfactory;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Constructor;

@Slf4j
public class ConstructorBasedSmartContextFactory extends AbstractSmartContextFactory {
    private final Constructor constructor;
    public ConstructorBasedSmartContextFactory(Class cmdClass, Class contextClass, Constructor constructor) {
        super(cmdClass, contextClass);
        Preconditions.checkArgument(constructor != null);
        this.constructor = constructor;
    }

    @Override
    public Object create(Object command) {
        try {
            return ConstructorUtils.invokeConstructor(contextClass, command);
        } catch (Exception e) {
            log.error("failed to invoke Constructor {} use {}", contextClass, command);
            throw new RuntimeException(e);
        }
    }
}
