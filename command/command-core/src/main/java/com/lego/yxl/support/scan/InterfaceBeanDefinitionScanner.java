package com.lego.yxl.support.scan;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

public class InterfaceBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private final BeanDefinitionRegistry registry;

    public InterfaceBeanDefinitionScanner(BeanDefinitionRegistry registry,
                                          Class<? extends Annotation> scanClass,
                                          Class<? extends Annotation> excludeClass) {
        super(false);
        this.registry = registry;

        super.addIncludeFilter(new AnnotationTypeFilter(scanClass, true, true));
        addExcludeFilter(new AnnotationTypeFilter(excludeClass));
    }

    @Override
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {

        Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);

        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }

        return candidates;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean isInterface = beanDefinition.getMetadata().isInterface();
        boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        return isInterface && isTopLevelType;
    }

    @NonNull
    @Override
    protected BeanDefinitionRegistry getRegistry() {
        return registry;
    }
}
