package com.lego.yxl.command.core.support.method;

import com.google.common.collect.Lists;
import com.lego.yxl.agg.AggRoot;
import com.lego.yxl.command.core.CommandForCreate;
import com.lego.yxl.command.core.support.handler.CommandHandler;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvoker;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvokerFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Setter
public class CreateServiceMethodInvokerFactory
        extends BaseCommandServiceMethodInvokerFactory
        implements ServiceMethodInvokerFactory {

    public CreateServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass) {
        super(aggClass);
    }

    @Override
    public ServiceMethodInvoker createForMethod(Method method) {
        if (method.getParameterCount() != 1){
            return null;
        }

        Class commandType = method.getParameterTypes()[0];
        if (!CommandForCreate.class.isAssignableFrom(commandType)){
            return null;
        }

        Class returnType = method.getReturnType();
        List<Class> context = Lists.newArrayList();
        Class contextFromConfig = getContextClass(method);
        if (contextFromConfig != null){
            context.add(contextFromConfig);
            this.parseContext(contextFromConfig);
        }else {
            context.addAll(getContextClassFromAggClass());
        }

        if (CollectionUtils.isEmpty(context)){
            log.info("Failed to find create Method for command {} on class {}", commandType, getAggClass());
            return null;
        }

        autoRegisterAggLoaders(commandType);

        List<CommandHandler> result = context.stream()
                .map(contextType -> this.getCommandHandlerFactory()
                        .createCreateAggCommandHandler(getAggClass(), commandType, contextType, returnType))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (result.size() != 1){
            log.warn("Failed to find create Method for command {} on class {}, more than one command handler is found {}", commandType, getAggClass(), result);
            return null;
        }

        return new CommandHandlerBasedServiceMethodInvoker(result.get(0));
    }

    private List<Class> getContextClassFromAggClass() {
        List<Class> context = Lists.newArrayList();
        for (Method aggMethod : this.getAggClass().getMethods()){
            int modifiers = aggMethod.getModifiers();
            if (!Modifier.isStatic(modifiers)){
                continue;
            }
            if (!Modifier.isPublic(modifiers)){
                continue;
            }
            int parameterCount = aggMethod.getParameterCount();
            if (parameterCount != 1){
                continue;
            }
            Class contextClass = aggMethod.getParameterTypes()[0];
            context.add(contextClass);
        }

        for (Constructor constructor : this.getAggClass().getConstructors()){
            int parameterCount = constructor.getParameterCount();
            if (parameterCount != 1){
                continue;
            }

            Class contextClass = constructor.getParameterTypes()[0];
            context.add(contextClass);
        }
        return context;
    }

}
