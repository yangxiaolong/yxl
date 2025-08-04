package com.lego.yxl.core.command.support.handler.preaction;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.stream.Collectors;

public class SmartPreOperations {
    private final List<SmartPreOperation> preOperations = Lists.newArrayList();

    public List<PreOperation> findByContext(Class context){
        return this.preOperations.stream()
                .filter(smartPreOperation -> smartPreOperation.support(context))
                .collect(Collectors.toList());
    }

    @Autowired(required = false)
    public void setPreOperations(List<SmartPreOperation> smartPreOperations){
        this.preOperations.addAll(smartPreOperations);
        AnnotationAwareOrderComparator.sort(this.preOperations);
    }
}
