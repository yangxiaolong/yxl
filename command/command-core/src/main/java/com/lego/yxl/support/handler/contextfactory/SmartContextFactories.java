package com.lego.yxl.support.handler.contextfactory;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Arrays;
import java.util.List;

public class SmartContextFactories {
    private final List<SmartContextFactory> smartContextFactories = Lists.newArrayList();

    public ContextFactory findContextFactory(Class cmdClass, Class contextClass){
        return this.smartContextFactories.stream()
                .filter(smartContextFactory -> smartContextFactory.apply(cmdClass, contextClass))
                .findFirst()
                .orElseThrow(() -> new ContextFactoryNotFoundException(cmdClass, contextClass));
    }

    public ContextFactory findContextFactoryOrNull(Class cmdClass, Class contextClass){
        return this.smartContextFactories.stream()
                .filter(smartContextFactory -> smartContextFactory.apply(cmdClass, contextClass))
                .findFirst()
                .orElse(null);
    }

    @Autowired
    public void setSmartContextFactories(List<SmartContextFactory> smartContextFactories){
        addSmartContextFactories(smartContextFactories);
    }

    public void addSmartContextFactory(SmartContextFactory smartContextFactory){
        addSmartContextFactories(Arrays.asList(smartContextFactory));
    }


    private void addSmartContextFactories(List<SmartContextFactory> smartContextFactories){
        this.smartContextFactories.addAll(smartContextFactories);
        AnnotationAwareOrderComparator.sort(this.smartContextFactories);
    }

}
