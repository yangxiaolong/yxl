package com.lego.yxl;

import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public class CommandApplicationServicesRegistry {
    @Autowired
    private ApplicationContext applicationContext;

    @Getter
    private List<Object> commandApplicationServices = Lists.newArrayList();

    @PostConstruct
    public void init(){
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(CommandApplicationServiceDefinition.class);
        this.commandApplicationServices.addAll(beansWithAnnotation.values());
    }
}
