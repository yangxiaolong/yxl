package com.lego.yxl.support.spring.invoker;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * 拆分器注册器，管理 Method 和 SplitInvoker 间的映射
 */
public class SplitInvokerRegistry {
    private final Map<Method, SplitInvoker> registryMap = Maps.newHashMap();

    public SplitInvoker getByMethod(Method method){
        return registryMap.get(method);
    }

    public void register(Method method, SplitInvoker splitInvoker){
        this.registryMap.put(method, splitInvoker);
    }
}
