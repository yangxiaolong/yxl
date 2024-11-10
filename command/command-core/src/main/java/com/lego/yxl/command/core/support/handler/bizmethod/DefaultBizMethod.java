package com.lego.yxl.command.core.support.handler.bizmethod;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;

@Value
public class DefaultBizMethod<AGG, CONTEXT> implements BiConsumer<AGG, CONTEXT>{
    private final Method method;

    private DefaultBizMethod(Method method){
        Preconditions.checkArgument(method != null);
        this.method = method;
    }

    @SneakyThrows
    @Override
    public void accept(AGG agg, CONTEXT context) {
        this.method.invoke(agg, context);
    }

    public static DefaultBizMethod apply(Method method){
        return new DefaultBizMethod(method);
    }

    @Override
    public String toString(){
        return method.toString();
    }
}
