package com.lego.yxl.command.core.support.handler;

import com.lego.yxl.AggRoot;
import com.lego.yxl.command.core.support.handler.aggfactory.SmartAggFactories;
import com.lego.yxl.command.core.support.handler.aggfactory.SmartAggFactory;
import com.lego.yxl.command.core.support.handler.aggloader.SmartAggLoader;
import com.lego.yxl.command.core.support.handler.aggloader.SmartAggLoaders;
import com.lego.yxl.command.core.support.handler.aggsyncer.SmartAggSyncer;
import com.lego.yxl.command.core.support.handler.aggsyncer.SmartAggSyncers;
import com.lego.yxl.command.core.support.handler.contextfactory.ContextFactory;
import com.lego.yxl.command.core.support.handler.contextfactory.SmartContextFactories;
import com.lego.yxl.command.core.support.handler.converter.ResultConverter;
import com.lego.yxl.command.core.support.handler.converter.SmartResultConverters;
import com.lego.yxl.command.core.support.handler.preaction.PreOperation;
import com.lego.yxl.command.core.support.handler.preaction.SmartPreOperations;
import com.lego.yxl.validator.core.ValidateService;
import com.lego.yxl.loader.core.lazyloadproxyfactory.LazyLoadProxyFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Setter
public class AggCommandHandlerFactories{
    @Autowired
    private ValidateService validateService;
    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SmartResultConverters smartResultConverters;
    @Autowired
    private SmartContextFactories smartContextFactories;
    @Autowired
    private SmartAggSyncers smartAggSyncers;
    @Autowired
    private SmartAggFactories smartAggFactories;
    @Autowired
    private SmartAggLoaders smartAggLoaders;
    @Autowired
    private SmartPreOperations smartPreOperations;


    public <CMD, RESULT> CommandHandler<CMD, RESULT> createCreateAggCommandHandler(Class aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggFactory aggFactory = this.smartAggFactories.findAggFactoryOrNull(contextClass, aggClass);
        if (aggFactory == null){
            return null;
        }
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }
        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }
        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        List<PreOperation> preOperations = this.smartPreOperations.findByContext(contextClass);

        CreateAggCommandHandler commandHandler = new CreateAggCommandHandler(this.validateService,
                this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate, preOperations
        );
        commandHandler.setAggFactory(aggFactory);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);

        commandHandler.validate();
        return commandHandler;
    }

    public UpdateAggCommandHandler createUpdateAggCommandHandler(Class aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }

        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }

        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        SmartAggLoader aggLoader = this.smartAggLoaders.findAggLoaderOrNull(cmdClass, aggClass);
        if (aggLoader == null){
            return null;
        }

        List<PreOperation> preOperations = this.smartPreOperations.findByContext(contextClass);

        UpdateAggCommandHandler commandHandler = new UpdateAggCommandHandler(this.validateService, preOperations, this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);
        commandHandler.setAggLoader(aggLoader);

        commandHandler.validate();
        return commandHandler;
    }

    public SyncAggCommandHandler createSyncAggCommandHandler(Class<? extends AggRoot> aggClass, Class cmdClass, Class contextClass, Class resultClass) {
        SmartAggSyncer aggSyncer = this.smartAggSyncers.findAggSyncerOrNull(aggClass);
        if (aggSyncer == null){
            return null;
        }

        ResultConverter resultConverter = this.smartResultConverters.findResultConverter(aggClass, contextClass, resultClass);
        if (resultConverter == null){
            return null;
        }

        ContextFactory contextFactory = this.smartContextFactories.findContextFactoryOrNull(cmdClass, contextClass);
        if (contextFactory == null){
            return null;
        }

        SmartAggLoader aggLoader = this.smartAggLoaders.findAggLoaderOrNull(cmdClass, aggClass);
        if (aggLoader == null){
            return null;
        }

        SmartAggFactory aggFactory = this.smartAggFactories.findAggFactoryOrNull(contextClass, aggClass);
        if (aggFactory == null){
            return null;
        }

        List<PreOperation> preOperations = this.smartPreOperations.findByContext(contextClass);

        SyncAggCommandHandler commandHandler = new SyncAggCommandHandler(this.validateService,
                preOperations,
                this.lazyLoadProxyFactory, this.eventPublisher, this.transactionTemplate);
        commandHandler.setAggSyncer(aggSyncer);
        commandHandler.setResultConverter(resultConverter);
        commandHandler.setContextFactory(contextFactory);
        commandHandler.setAggLoader(aggLoader);
        commandHandler.setAggFactory(aggFactory);

        commandHandler.validate();
        return commandHandler;
    }
}
