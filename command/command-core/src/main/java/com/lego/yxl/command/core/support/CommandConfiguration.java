package com.lego.yxl.command.core.support;

import com.lego.yxl.command.core.CommandServicesRegistry;
import com.lego.yxl.command.core.support.handler.AggCommandHandlerFactories;
import com.lego.yxl.command.core.support.handler.CommandParser;
import com.lego.yxl.command.core.support.handler.aggfactory.SmartAggFactories;
import com.lego.yxl.command.core.support.handler.aggloader.SmartAggLoaders;
import com.lego.yxl.command.core.support.handler.aggsyncer.SmartAggSyncers;
import com.lego.yxl.command.core.support.handler.contextfactory.EqualsSmartContextFactory;
import com.lego.yxl.command.core.support.handler.contextfactory.SmartContextFactories;
import com.lego.yxl.command.core.support.handler.converter.AggSmartResultConverter;
import com.lego.yxl.command.core.support.handler.converter.BooleanResultConverter;
import com.lego.yxl.command.core.support.handler.converter.SmartResultConverters;
import com.lego.yxl.command.core.support.handler.converter.VoidResultConverter;
import com.lego.yxl.command.core.support.handler.preaction.SmartPreOperations;
import com.lego.yxl.command.core.CommandApplicationServicesRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfiguration {

    @Bean
    public CommandServicesRegistry commandServicesRegistry(){
        return new CommandServicesRegistry();
    }

    @Bean
    public CommandApplicationServicesRegistry commandApplicationServicesRegistry(){
        return new CommandApplicationServicesRegistry();
    }
    @Bean
    public CommandApplicationServiceProxyFactory commandApplicationServiceProxyFactory(){
        return new CommandApplicationServiceProxyFactory();
    }

    @Bean
    public CommandServiceProxyFactory commandServiceProxyFactory(){
        return new CommandServiceProxyFactory();
    }

    @Bean
    public SmartAggFactories smartAggFactories(){
        return new SmartAggFactories();
    }

    @Bean
    public AggCommandHandlerFactories aggCommandHandlerFactories(){
        return new AggCommandHandlerFactories();
    }

    @Bean
    public CommandParser commandParser(){
        return new CommandParser();
    }

    @Bean
    public SmartAggLoaders smartAggLoaders(){
        return new SmartAggLoaders();
    }

    @Bean
    public SmartPreOperations smartPreOperations(){
        return new SmartPreOperations();
    }

    @Bean
    public SmartAggSyncers smartAggSyncers(){
        return new SmartAggSyncers();
    }

    @Bean
    public EqualsSmartContextFactory equalsSmartContextFactory(){
        return new EqualsSmartContextFactory();
    }

    @Bean
    public SmartContextFactories smartContextFactories(){
        return new SmartContextFactories();
    }

    @Bean
    public AggSmartResultConverter aggSmartResultConverter(){
        return new AggSmartResultConverter();
    }

    @Bean
    public BooleanResultConverter booleanResultConverter(){
        return new BooleanResultConverter();
    }

    @Bean
    public SmartResultConverters smartResultConverters(){
        return new SmartResultConverters();
    }

    @Bean
    public VoidResultConverter voidResultConverter(){
        return new VoidResultConverter();
    }
}
