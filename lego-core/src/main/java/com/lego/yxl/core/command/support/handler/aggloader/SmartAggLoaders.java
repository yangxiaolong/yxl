package com.lego.yxl.core.command.support.handler.aggloader;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Arrays;
import java.util.List;

public class SmartAggLoaders {
    private final List<SmartAggLoader> smartAggLoaders = Lists.newArrayList();

    public SmartAggLoader findAggLoader(Class cmdClass, Class aggClass){
        return this.smartAggLoaders.stream()
                .filter(smartAggLoader -> smartAggLoader.apply(cmdClass, aggClass))
                .findFirst()
                .orElseThrow(() -> new AggLoaderNotFoundException(cmdClass, aggClass));
    }

    public SmartAggLoader findAggLoaderOrNull(Class cmdClass, Class aggClass){
        return this.smartAggLoaders.stream()
                .filter(smartAggLoader -> smartAggLoader.apply(cmdClass, aggClass))
                .findFirst()
                .orElse(null);
    }

    @Autowired(required = false)
    public void setSmartAggLoaders(List<SmartAggLoader> smartAggLoaders){
        addSmartAggLoaders(smartAggLoaders);
    }

    public void addSmartAggLoader(SmartAggLoader smartAggLoader){
        addSmartAggLoaders(Arrays.asList(smartAggLoader));
    }

    private void addSmartAggLoaders(List<SmartAggLoader> smartAggLoaders){
        this.smartAggLoaders.addAll(smartAggLoaders);
        AnnotationAwareOrderComparator.sort(this.smartAggLoaders);
    }
}
