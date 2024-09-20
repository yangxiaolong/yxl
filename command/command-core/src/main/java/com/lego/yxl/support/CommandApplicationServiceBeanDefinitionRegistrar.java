package com.lego.yxl.support;

import com.lego.yxl.CommandApplicationServiceDefinition;
import com.lego.yxl.EnableCommandApplicationService;
import com.lego.yxl.NoCommandApplicationService;
import com.lego.yxl.support.scan.InterfaceBeanDefinitionScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Set;

public class CommandApplicationServiceBeanDefinitionRegistrar
        implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware,
        EnvironmentAware {
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry,
                                        BeanNameGenerator beanNameGenerator) {
        if (metadata.getAnnotationAttributes(EnableCommandApplicationService.class.getName()) == null) {
            return;
        }

        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(EnableCommandApplicationService.class.getName());
        String[] paths = (String[]) annotationAttributes.get("basePackages");

        InterfaceBeanDefinitionScanner scanner = new InterfaceBeanDefinitionScanner(registry,
                CommandApplicationServiceDefinition.class,
                NoCommandApplicationService.class);
        scanner.setEnvironment(environment);
        scanner.setResourceLoader(resourceLoader);
        for (String path : paths){
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(path);
            candidateComponents.forEach(beanDefinition -> {
                BeanDefinition beanDefinitionToUse = buildFactoryBean(beanDefinition);
                String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);
                registry.registerBeanDefinition(beanName, beanDefinitionToUse);
            });
        }
    }

    private BeanDefinition buildFactoryBean(BeanDefinition beanDefinition) {
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder
                .rootBeanDefinition(CommandApplicationServiceProxyFactoryBean.class);
        definitionBuilder.addConstructorArgValue(beanDefinition.getBeanClassName());
        return definitionBuilder.getBeanDefinition();
    }
}
