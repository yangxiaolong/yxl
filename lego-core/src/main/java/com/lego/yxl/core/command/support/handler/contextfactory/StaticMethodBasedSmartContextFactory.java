package com.lego.yxl.core.command.support.handler.contextfactory;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Method;

@Slf4j
public class StaticMethodBasedSmartContextFactory
        extends AbstractSmartContextFactory {
    private final Method method;

    public StaticMethodBasedSmartContextFactory(Class cmdClass, Class contextClass, Method method) {
        super(cmdClass, contextClass);
        Preconditions.checkArgument(method != null);
        this.method = method;
    }

    @Override
    public Object create(Object command) {
        try {
            return MethodUtils.invokeStaticMethod(this.contextClass, method.getName(), command);
        } catch (Exception e) {
            log.error("failed to invoker static method {} use {}", method, command);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(){
        return this.method.toString();
    }
}
