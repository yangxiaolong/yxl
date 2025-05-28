package com.lego.yxl.command.core.support.method;

import com.google.common.collect.Lists;
import com.lego.yxl.agg.AggRoot;
import com.lego.yxl.command.core.support.handler.CommandHandler;
import com.lego.yxl.command.core.support.handler.SyncAggCommandHandler;
import com.lego.yxl.command.core.support.handler.bizmethod.DefaultBizMethod;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvoker;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvokerFactory;
import com.lego.yxl.command.core.CommandForSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static ch.qos.logback.core.joran.util.beans.BeanUtil.isSetter;

@Slf4j
public class SyncServiceMethodInvokerFactory
        extends BaseCommandServiceMethodInvokerFactory
        implements ServiceMethodInvokerFactory {

    public SyncServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass) {
        super(aggClass);
    }

    @Override
    public ServiceMethodInvoker createForMethod(Method method) {
        if (method.getParameterCount() != 1){
            return null;
        }

        Class commandType = method.getParameterTypes()[0];
        if (!CommandForSync.class.isAssignableFrom(commandType)){
            return null;
        }

        Class returnType = method.getReturnType();

        List<BizMethodContext> contexts = Lists.newArrayList();
        for (Method aggMethod : this.getAggClass().getMethods()){
            int modifiers = aggMethod.getModifiers();
            if (Modifier.isStatic(modifiers)){
                continue;
            }
            if (!Modifier.isPublic(modifiers)){
                continue;
            }

            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1){
                continue;
            }
            if (isSetter(aggMethod)){
                continue;
            }

            Class aggParamType = aggMethod.getParameterTypes()[0];
            contexts.add(new BizMethodContext(aggParamType, aggMethod));
        }

        if (CollectionUtils.isEmpty(contexts)){
            return null;
        }

        autoRegisterAggLoaders(commandType);
        List<CommandHandler> result = Lists.newArrayList();
        for (BizMethodContext context : contexts){
            SyncAggCommandHandler commandHandler = this.getCommandHandlerFactory()
                    .createSyncAggCommandHandler(getAggClass(), commandType, context.getContextClass(), returnType);
            if (commandHandler == null){
                continue;
            }
            result.add(commandHandler);
            commandHandler.addBizMethod(DefaultBizMethod.apply(context.getMethod()));
        }

        if (result.size() != 1){
            log.warn("Failed to find create Method for command {} on class {}, more than one command handler is found {}", commandType, getAggClass(), result);
            return null;
        }

        return new CommandHandlerBasedServiceMethodInvoker(result.get(0));
    }
}
