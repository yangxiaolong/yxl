package com.lego.yxl.command.core.support.handler;

import com.google.common.collect.Sets;
import com.lego.yxl.command.core.support.handler.aggfactory.ConstructorBasedSmartAggFactory;
import com.lego.yxl.command.core.support.handler.aggfactory.SmartAggFactories;
import com.lego.yxl.command.core.support.handler.aggfactory.StaticMethodBasedSmartAggFactory;
import com.lego.yxl.command.core.support.handler.contextfactory.ConstructorBasedSmartContextFactory;
import com.lego.yxl.command.core.support.handler.contextfactory.SmartContextFactories;
import com.lego.yxl.command.core.support.handler.contextfactory.StaticMethodBasedSmartContextFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

public class CommandParser {
    private final Set<Class> parsedAggClass = Sets.newHashSet();
    private final Set<Class> parsedContextClass = Sets.newHashSet();
    @Autowired
    private SmartAggFactories smartAggFactories;
    @Autowired
    private SmartContextFactories smartContextFactories;

    /**
     * 解析 Context，并注册 ContextFactory
     * @param context
     */
    public void parseContext(Class context){
        if (this.parsedContextClass.contains(context)){
            return;
        }

        this.parsedContextClass.add(context);

        for (Method aggMethod : context.getDeclaredMethods()) {
            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1) {
                // 参数不为 1，直接跳过
                continue;
            }

            Class paramClass = aggMethod.getParameterTypes()[0];
            int modifiers = aggMethod.getModifiers();
            // 静态方法
            if (Modifier.isStatic(modifiers)) {
                StaticMethodBasedSmartContextFactory contextFactory = new StaticMethodBasedSmartContextFactory(paramClass, context, aggMethod);
                this.smartContextFactories.addSmartContextFactory(contextFactory);
            }
        }
        for (Constructor constructor : context.getConstructors()){
            if (constructor.getParameterCount() != 1){
                // 参数不为 1，直接跳过
                continue;
            }
            Class paramClass = constructor.getParameterTypes()[0];
            ConstructorBasedSmartContextFactory contextFactory = new ConstructorBasedSmartContextFactory(paramClass, context, constructor);
            this.smartContextFactories.addSmartContextFactory(contextFactory);
        }
    }

    /**
     * 解析 聚合根，并注册相关的 AggFactory
     * @param agg
     */
    public void parseAgg(Class agg) {
        if (this.parsedAggClass.contains(agg)){
            return;
        }
        this.parsedAggClass.add(agg);

        for (Method aggMethod : agg.getDeclaredMethods()) {
            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1) {
                // 参数不为 1，直接跳过
                continue;
            }

            Class paramClass = aggMethod.getParameterTypes()[0];
            int modifiers = aggMethod.getModifiers();
            // 静态方法，注册为 AggFactory 方法
            if (Modifier.isStatic(modifiers)) {
                StaticMethodBasedSmartAggFactory aggFactory = new StaticMethodBasedSmartAggFactory(paramClass, agg, aggMethod);
                this.smartAggFactories.addSmartAggFactory(aggFactory);
                parseContext(paramClass);
            }else {
                parseContext(paramClass);
            }
        }
        // 单参数构造函数，自动注册为 AggFactory
        for (Constructor constructor : agg.getConstructors()){
            if (constructor.getParameterCount() != 1){
                // 参数不为 1，直接跳过
                continue;
            }
            Class paramClass = constructor.getParameterTypes()[0];
            ConstructorBasedSmartAggFactory smartAggFactory = new ConstructorBasedSmartAggFactory(paramClass, agg, constructor);
            this.smartAggFactories.addSmartAggFactory(smartAggFactory);
            parseContext(paramClass);
        }
    }
}
