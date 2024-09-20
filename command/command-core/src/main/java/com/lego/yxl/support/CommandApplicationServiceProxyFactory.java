package com.lego.yxl.support;

import com.google.common.collect.Sets;
import com.lego.yxl.CommandService;
import com.lego.yxl.CommandServicesRegistry;
import com.lego.yxl.NoCommandApplicationService;
import com.lego.yxl.CommandRepository;
import com.lego.yxl.support.intercepter.MethodDispatcherInterceptor;
import com.lego.yxl.support.invoker.CommandServiceBasedServiceMethodInvoker;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Set;

@Setter(AccessLevel.PRIVATE)
@Slf4j
public class CommandApplicationServiceProxyFactory
        extends BaseCommandProxyFactory {

    @Autowired
    private CommandServicesRegistry commandServicesRegistry;


    public <T> T createCommandApplicationService(Class commandService){
        return super.createProxy(commandService);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected CommandServiceMetadata buildMetadata(Class commandService) {
        return CommandServiceMetadata.fromCommandApplicationService(commandService);
    }

    @Override
    protected Class getSkipAnnotation() {
        return NoCommandApplicationService.class;
    }

    @Override
    protected void beforeRegisterInterceptors(Class commandService, Set<Method> methods, CommandRepository repository, CommandServiceMetadata metadata, ProxyFactory result) {
        MethodDispatcherInterceptor commandServiceMethodDispatcherInterceptor = createCommandServiceMethodDispatcherInterceptor(methods, repository, metadata);
        log.info("Auto Register CommandServiceMethod for {} : {}", commandService, commandServiceMethodDispatcherInterceptor);
        result.addAdvice(commandServiceMethodDispatcherInterceptor);
    }

    private MethodDispatcherInterceptor createCommandServiceMethodDispatcherInterceptor(Set<Method> methods,
                                                                                        CommandRepository repository,
                                                                                        CommandServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();
        Set<Method> methodsForRemove = Sets.newHashSet();
        for (Method method : methods) {
            if (methodsForRemove.contains(method)){
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1){
                continue;
            }
            Class parameterType = parameterTypes[0];
            Class resultType = method.getReturnType();

            CommandService commandServiceToUse = null;

            for (CommandService commandService : this.commandServicesRegistry.getCommandServices()){
                Method executeMethod = MethodUtils.getMatchingMethod(commandService.getClass(), CommandService.METHOD_NAME, parameterType);
                if (executeMethod != null && executeMethod.getReturnType() == resultType){
                    commandServiceToUse = commandService;
                    break;
                }
            }
            if (commandServiceToUse != null){
                Set<Method> overrideHierarchy = MethodUtils.getOverrideHierarchy(method, ClassUtils.Interfaces.INCLUDE);
                for (Method override : overrideHierarchy){
                    methodDispatcher.register(override, new CommandServiceBasedServiceMethodInvoker(commandServiceToUse));
                    methodsForRemove.add(override);
                }
            }
        }
        methods.removeAll(methodsForRemove);
        return methodDispatcher;
    }




}
