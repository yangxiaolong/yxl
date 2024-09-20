package com.lego.yxl.support.handler.converter;

import com.google.common.collect.Lists;
import com.lego.yxl.AggRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

public class SmartResultConverters {
    private final List<SmartResultConverter> smartResultConverters = Lists.newArrayList();

    public <AGG extends AggRoot<?>, CONTEXT, RESULT> ResultConverter<AGG, CONTEXT, RESULT> findResultConverter(
            Class<AGG> aggClass, Class<CONTEXT> contextClass, Class<RESULT> resultClass){
        return this.smartResultConverters.stream()
                .filter(smartResultConverter -> smartResultConverter.apply(aggClass, contextClass, resultClass))
                .findFirst()
                .orElseThrow(() -> new ResultConverterNotFoundException(aggClass, contextClass, resultClass));
    }

    @Autowired
    public void setSmartResultConverters(List<SmartResultConverter> smartResultConverters){
        this.smartResultConverters.addAll(smartResultConverters);
        AnnotationAwareOrderComparator.sort(this.smartResultConverters);
    }
}
