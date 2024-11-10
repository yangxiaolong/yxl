package com.lego.yxl.command.core.support.handler.aggfactory;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Arrays;
import java.util.List;


public class SmartAggFactories{
    private final List<SmartAggFactory> smartAggFactories = Lists.newArrayList();

    public SmartAggFactory findAggFactory(Class contextClass, Class aggClass){
        return this.smartAggFactories.stream()
                .filter(smartAggFactory -> smartAggFactory.apply(contextClass, aggClass))
                .findFirst()
                .orElseThrow(()->new AggFactoryNotFound(contextClass, aggClass));
    }

    public SmartAggFactory findAggFactoryOrNull(Class contextClass, Class aggClass){
        return this.smartAggFactories.stream()
                .filter(smartAggFactory -> smartAggFactory.apply(contextClass, aggClass))
                .findFirst()
                .orElse(null);
    }

    @Autowired(required = false)
    public void setSmartAggFactories(List<SmartAggFactory> smartAggFactories){
        addSmartAggFactories(smartAggFactories);
    }

    public void addSmartAggFactory(SmartAggFactory aggFactory){
        addSmartAggFactories(Arrays.asList(aggFactory));
    }

    private void addSmartAggFactories(List<SmartAggFactory> smartAggFactories){
        this.smartAggFactories.addAll(smartAggFactories);
        AnnotationAwareOrderComparator.sort(this.smartAggFactories);
    }
}
