package com.lego.yxl.command.core.support;

import com.google.common.collect.Sets;
import com.lego.yxl.command.core.support.intercepter.MethodDispatcherInterceptor;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvoker;
import com.lego.yxl.command.core.support.invoker.ServiceMethodInvokerFactory;
import com.lego.yxl.command.core.support.invoker.TargetBasedServiceMethodInvoker;
import com.lego.yxl.command.core.support.invoker.TargetBasedServiceMethodInvokerFactory;
import com.lego.yxl.command.core.support.method.CreateServiceMethodInvokerFactory;
import com.lego.yxl.command.core.support.method.SyncServiceMethodInvokerFactory;
import com.lego.yxl.command.core.support.method.UpdateServiceMethodInvokerFactory;
import com.lego.yxl.command.core.support.proxy.DefaultProxyObject;
import com.lego.yxl.command.core.support.proxy.ProxyObject;
import com.lego.yxl.command.core.CommandRepository;
import com.lego.yxl.command.core.CommandServiceMethodLostException;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.transaction.interceptor.TransactionalProxy;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

abstract class BaseCommandProxyFactory implements BeanClassLoaderAware {
    @Getter(AccessLevel.PROTECTED)
    private ClassLoader classLoader;
    @Autowired
    @Getter(AccessLevel.PROTECTED)
    private ApplicationContext applicationContext;


    protected abstract Logger getLogger();

    protected abstract CommandServiceMetadata buildMetadata(Class commandService);

    protected abstract Class getSkipAnnotation();

    protected void beforeRegisterInterceptors(Class commandService,
                                              Set<Method> methods,
                                              CommandRepository repository,
                                              CommandServiceMetadata metadata,
                                              ProxyFactory result) {
    }

    public <T> T createProxy(Class commandService){
        CommandServiceMetadata metadata = buildMetadata(commandService);

        Object target = createTargetService(metadata);
        ProxyFactory result = new ProxyFactory();

        // 设置代理的目标对象
        result.setTarget(target);
        // 设置代理类实现的 接口
        result.setInterfaces(metadata.getServiceClass(), ProxyObject.class, TransactionalProxy.class);

        // 收集所有的方法，初始化时进行校验
        Set<Method> methods = Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(commandService));
        methods.addAll(Sets.newHashSet(ReflectionUtils.getAllDeclaredMethods(ProxyObject.class)));


        // 对 default 方法进行拦截和转发
        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(commandService)) {
            result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
            Set<Method> methodsForRemove = methods.stream()
                    .filter(Method::isDefault)
                    .collect(Collectors.toSet());
            // 移除默认方法
            Set<Method> all = methodsForRemove.stream()
                    .flatMap(method -> MethodUtils.getOverrideHierarchy(method, ClassUtils.Interfaces.INCLUDE).stream())
                    .collect(Collectors.toSet());
            methods.removeAll(all);
        }

        // 对所有的实现进行封装，基于拦截器进行请求转发
        // 1. target 对象封装
        result.addAdvice(createTargetDispatcherInterceptor(target, methods));

        // 添加 ProxyObject 提供 Meta信息
        result.addAdvice(createTargetDispatcherInterceptor(new DefaultProxyObject(metadata.getServiceClass()), methods));

        // 2. 自定义实现类封装
        List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(metadata.getServiceClass());
        for (Class itfCls : allInterfaces){
            if (itfCls.getAnnotation(getSkipAnnotation()) != null){
                continue;
            }
            String clsName = itfCls.getSimpleName();
            String beanName = Character.toLowerCase(clsName.charAt(0)) + clsName.substring(1, clsName.length()) + "Impl";
            Object bean = getApplicationContext().getBean(beanName, itfCls);
            if (bean != null){
                result.addAdvice(createTargetDispatcherInterceptor(bean, methods));
            }
        }

        // 3. 自动解析方法封装
        Class repositoryClass = metadata.getRepositoryClass();

        CommandRepository repository = (CommandRepository) this.getApplicationContext().getBean(repositoryClass);

        beforeRegisterInterceptors(commandService, methods, repository, metadata, result);

        MethodDispatcherInterceptor syncMethodDispatcherInterceptor = createSyncMethodDispatcherInterceptor(methods, repository, metadata);
        getLogger().info("Auto Register SyncMethod for {} : {}", commandService, syncMethodDispatcherInterceptor);
        result.addAdvice(syncMethodDispatcherInterceptor);


        MethodDispatcherInterceptor createMethodDispatcher = createCreateMethodDispatcherInterceptor(methods, repository, metadata);
        getLogger().info("Auto Register CreateMethod for {} : {}", commandService, createMethodDispatcher);
        result.addAdvice(createMethodDispatcher);


        MethodDispatcherInterceptor updateMethodDispatcher = createUpdateMethodDispatcherInterceptor(methods, repository, metadata);
        getLogger().info("Auto Register UpdateMethod for {} : {}", commandService, updateMethodDispatcher);
        result.addAdvice(updateMethodDispatcher);

        MethodDispatcherInterceptor selectMethodDispatcher = createSelectMethodDispatcherInterceptor(methods, repository, metadata);
        getLogger().info("Auto Register SelectMethod for {} : {}", commandService, selectMethodDispatcher);
        result.addAdvice(selectMethodDispatcher);


        if (!CollectionUtils.isEmpty(methods)){
            getLogger().error("Method Lost for {}, lost method is {}", commandService, methods);
            throw new CommandServiceMethodLostException(methods);
        }

        T proxy = (T) result.getProxy(getClassLoader());
        return proxy;
    }


    protected MethodDispatcherInterceptor createSelectMethodDispatcherInterceptor(Set<Method> methods,
                                                                                  CommandRepository repository,
                                                                                  CommandServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();
        Set<Method> methodsForRemove = Sets.newHashSet();
        for (Method method : methods) {
            if (methodsForRemove.contains(method)){
                continue;
            }
            Method invokeMethod = MethodUtils
                    .getMatchingAccessibleMethod(repository.getClass(), method.getName(), method.getParameterTypes());
            if (invokeMethod != null){
                Set<Method> overrideHierarchy = MethodUtils.getOverrideHierarchy(method, ClassUtils.Interfaces.INCLUDE);
                for (Method override : overrideHierarchy){
                    methodDispatcher.register(override, new TargetBasedServiceMethodInvoker(repository, invokeMethod));
                    methodsForRemove.add(override);
                }
            }
        }
        methods.removeAll(methodsForRemove);
        return methodDispatcher;
    }

    protected MethodDispatcherInterceptor createCreateMethodDispatcherInterceptor(Set<Method> methods, CommandRepository repository, CommandServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();

        CreateServiceMethodInvokerFactory methodInvokerFactory = new CreateServiceMethodInvokerFactory(metadata.getDomainClass());
        methodInvokerFactory.setCommandRepository(repository);
        this.applicationContext.getAutowireCapableBeanFactory().autowireBean(methodInvokerFactory);
        methodInvokerFactory.init();

        registerMethodInvokers(methods, methodDispatcher, methodInvokerFactory);
        return methodDispatcher;
    }

    protected MethodDispatcherInterceptor createUpdateMethodDispatcherInterceptor(Set<Method> methods, CommandRepository repository, CommandServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();

        UpdateServiceMethodInvokerFactory methodInvokerFactory = new UpdateServiceMethodInvokerFactory(metadata.getDomainClass());
        methodInvokerFactory.setCommandRepository(repository);
        this.applicationContext.getAutowireCapableBeanFactory().autowireBean(methodInvokerFactory);
        methodInvokerFactory.init();

        registerMethodInvokers(methods, methodDispatcher, methodInvokerFactory);
        return methodDispatcher;
    }

    protected MethodDispatcherInterceptor createSyncMethodDispatcherInterceptor(Set<Method> methods, CommandRepository repository, CommandServiceMetadata metadata) {
        MethodDispatcherInterceptor methodDispatcher = new MethodDispatcherInterceptor();

        SyncServiceMethodInvokerFactory methodInvokerFactory = new SyncServiceMethodInvokerFactory(metadata.getDomainClass());
        methodInvokerFactory.setCommandRepository(repository);
        this.applicationContext.getAutowireCapableBeanFactory().autowireBean(methodInvokerFactory);
        methodInvokerFactory.init();


        registerMethodInvokers(methods, methodDispatcher, methodInvokerFactory);
        return methodDispatcher;
    }

    protected MethodDispatcherInterceptor createTargetDispatcherInterceptor(Object target, Set<Method> methods){
        MethodDispatcherInterceptor targetMethodDispatcher = new MethodDispatcherInterceptor();
        TargetBasedServiceMethodInvokerFactory targetBasedQueryServiceMethodFactory = new TargetBasedServiceMethodInvokerFactory(target);

        registerMethodInvokers(methods, targetMethodDispatcher, targetBasedQueryServiceMethodFactory);

        return targetMethodDispatcher;
    }

    void registerMethodInvokers(Set<Method> methods,
                                MethodDispatcherInterceptor targetMethodDispatcher,
                                ServiceMethodInvokerFactory serviceMethodInvokerFactory) {
        Set<Method> methodsForRemove = Sets.newHashSet();
        for (Method callMethod : methods){
            if (methodsForRemove.contains(callMethod)){
                continue;
            }
            ServiceMethodInvoker exeMethod = serviceMethodInvokerFactory.createForMethod(callMethod);
            if (exeMethod != null){
                Set<Method> overrideHierarchy = MethodUtils.getOverrideHierarchy(callMethod, ClassUtils.Interfaces.INCLUDE);
                for (Method hMethod : overrideHierarchy) {
                    targetMethodDispatcher.register(hMethod, exeMethod);
                    methodsForRemove.add(hMethod);
                }
            }
        }
        methods.removeAll(methodsForRemove);
    }

    protected Object createTargetService(CommandServiceMetadata metadata) {
        return new Object();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
