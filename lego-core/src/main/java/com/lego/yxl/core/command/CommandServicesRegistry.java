package com.lego.yxl.core.command;

import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;


public class CommandServicesRegistry {
    @Autowired
    private ApplicationContext applicationContext;

    @Getter
    private List<CommandService> commandServices = Lists.newArrayList();

    @PostConstruct
    public void init(){
        Map<String, CommandService> commandServices = applicationContext.getBeansOfType(CommandService.class);
        this.commandServices.addAll(commandServices.values());
    }
}
