package com.lego.yxl.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.AggRoot;
import com.lego.yxl.Command;
import com.lego.yxl.CommandRepository;
import com.lego.yxl.ValidateService;
import com.lego.yxl.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.support.handler.CreateAggCommandHandler;
import com.lego.yxl.support.handler.SyncAggCommandHandler;
import com.lego.yxl.support.handler.UpdateAggCommandHandler;
import com.lego.yxl.support.handler.aggfactory.AggFactory;
import com.lego.yxl.support.handler.aggsyncer.CommandRepositoryBasedAggSyncer;
import com.lego.yxl.support.handler.contextfactory.ContextFactory;
import com.lego.yxl.support.handler.contextfactory.EqualsContextFactory;
import com.lego.yxl.support.handler.converter.AggResultConverter;
import com.lego.yxl.support.handler.preaction.SmartPreOperations;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public abstract class AbstractCommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandService.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SmartPreOperations smartPreOperations;

    /**
     * 创建 Creator，已完成对创建流程的组装
     * @param repository
     * @param <AGG>>
     * @param <CMD>>
     * @param <CONTEXT>>
     * @return
     */
    protected <AGG extends AggRoot<?>, CMD, CONTEXT> Creator<AGG, CMD, CONTEXT> creatorFor(CommandRepository<AGG, ?> repository){
        return new Creator<AGG, CMD, CONTEXT>(repository);
    }

    /**
     * 创建 Updater，已完成对更新流程的组装
     * @param aggregateRepository
     * @param <AGG>>
     * @param <CMD>>
     * @param <CONTEXT>
     * @return
     */
    protected <AGG extends AggRoot<?>,
            CMD,
            CONTEXT>
    Updater<AGG, CMD, CONTEXT> updaterFor(CommandRepository<AGG, ?> aggregateRepository){
        return new Updater<AGG, CMD, CONTEXT>(aggregateRepository);
    }

    /**
     * 创建 Syncer，已完成对同步流程的组装
     * @param aggregateRepository
     * @param <AGG>
     * @param <CMD>
     * @param <CONTEXT>
     * @return
     */
    protected <AGG extends AggRoot<?>,
            CMD extends Command,
            CONTEXT>
    Syncer<AGG, CMD, CONTEXT> syncerFor(CommandRepository<AGG, ?> aggregateRepository){
        return new Syncer<AGG, CMD, CONTEXT> (aggregateRepository);
    }

    /**
     * 创建流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <AGG>
     * @param <CMD>
     */
    protected class Creator<AGG extends AggRoot<?>, CMD, CONTEXT>{
        // 标准仓库
        private final CreateAggCommandHandler commandHandler;


        Creator(CommandRepository<AGG, ?> aggregateRepository) {
            Preconditions.checkArgument(aggregateRepository != null);
            this.commandHandler = new CreateAggCommandHandler(validateService, lazyLoadProxyFactory, eventPublisher, transactionTemplate, null);
            this.commandHandler.setContextFactory(EqualsContextFactory.getInstance());
            this.commandHandler.setResultConverter(AggResultConverter.getInstance());
            this.commandHandler.setAggSyncer(new CommandRepositoryBasedAggSyncer(aggregateRepository));
        }

        /**
         * 设置 聚合的实例化器
         * @param instanceFun
         * @return
         */
        public Creator<AGG, CMD, CONTEXT> instance(AggFactory<CMD, AGG> instanceFun){
            this.commandHandler.setAggFactory(instanceFun);
            return this;
        }

        /**
         * 增加 聚合上的业务操作，链式模式，可以绑定多的 业务操作
         * @param updater
         * @return
         */
        public Creator<AGG, CMD, CONTEXT> update(BiConsumer<AGG, CMD> updater){
            this.commandHandler.addBizMethod(updater);
            return this;
        }

        /**
         * 增加 成功处理器，链式模式，可以绑定多个处理器
         * @param onSuccessFun
         * @return
         */
        public Creator<AGG, CMD, CONTEXT> onSuccess(BiConsumer<AGG, CMD>  onSuccessFun){
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 增加 异常处理器，链式模式，可以绑定多个处理器
         * @param errorFun
         * @return
         */
        public Creator<AGG, CMD, CONTEXT> onError(BiConsumer<Exception, CMD>  errorFun){
            this.commandHandler.addOnError(errorFun);
            return this;
        }

        /**
         * 执行 创建流程
         * @param cmd
         * @return
         */
        public AGG call(CMD cmd){
            this.commandHandler.validate();
            return (AGG) commandHandler.handle(cmd);
        }
    }

    /**
     * 更新流程的 Builder，主要：
     * 1. 组装流程
     * 2. 执行流程
     * @param <AGG>>
     * @param <CONTEXT>
     */
    protected class Updater<AGG extends AggRoot<?>,
            CMD,
            CONTEXT> {
        private final UpdateAggCommandHandler<AGG, CMD, CONTEXT, AGG> commandHandler;

        Updater(CommandRepository<AGG, ?> aggregateRepository) {
            this.commandHandler = new UpdateAggCommandHandler<>(validateService, null, lazyLoadProxyFactory, eventPublisher, transactionTemplate);
            this.commandHandler.setAggSyncer(new CommandRepositoryBasedAggSyncer(aggregateRepository));
            this.commandHandler.setResultConverter(AggResultConverter.getInstance());
        }

        public Updater<AGG, CMD, CONTEXT> contextFactory(ContextFactory<CMD, CONTEXT> contextFactory){
            this.commandHandler.setContextFactory(contextFactory);
            return this;
        }

        /**
         * 设置 聚合对象加载器，用于从 DB 中加载 聚合
         * @param loader
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> loadBy(Function<CMD, Optional<AGG>> loader){
            this.commandHandler.setAggLoader(((cmd) -> loader.apply(cmd)));
            return this;
        }

        /**
         * 增加 业务执行器，链式模式，可以绑定多个执行器
         * @param updateFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> update(BiConsumer<AGG, CONTEXT> updateFun){
            this.commandHandler.addBizMethod(updateFun);
            return this;
        }

        /**
         * 增加 成功回调器，链式模式，可以绑定多个回调器
         * @param onSuccessFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 增加 异常回调器，链式模式，可以绑定多个回调器
         * @param errorFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onError(BiConsumer<Exception, CONTEXT>  errorFun){
            this.commandHandler.addOnError(errorFun);
            return this;
        }

        /**
         * 增加 聚合丢失处理器，链式模式，可以绑定多个回调器
         * @param onNotExistFun
         * @return
         */
        public Updater<AGG, CMD, CONTEXT> onNotFound(Consumer<CONTEXT>  onNotExistFun){
            this.commandHandler.addOnNotFound(onNotExistFun);
            return this;
        }

        /**
         * 执行更新流程
         * @param cmd
         * @return
         */
        public AGG exe(CMD cmd){
            this.commandHandler.validate();
            return commandHandler.handle(cmd);

        }
    }

    protected class Syncer<
            AGG extends AggRoot<?>,
            CMD,
            CONTEXT> {
        private final SyncAggCommandHandler<AGG, CMD, CONTEXT, AGG> commandHandler;

        Syncer(CommandRepository<AGG, ?> aggregateRepository) {
            this.commandHandler = new SyncAggCommandHandler(validateService, null, lazyLoadProxyFactory, eventPublisher, transactionTemplate);
            this.commandHandler.setResultConverter(AggResultConverter.getInstance());
            this.commandHandler.setAggSyncer(new CommandRepositoryBasedAggSyncer<>(aggregateRepository));
        }

        public Syncer<AGG, CMD, CONTEXT> contextFactory(ContextFactory<CMD, CONTEXT> contextFactory){
            this.commandHandler.setContextFactory(contextFactory);
            return this;
        }

        /**
         * 初始化器
         * @param instanceFun
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> instanceBy(AggFactory<CONTEXT, AGG> instanceFun){
            this.commandHandler.setAggFactory(instanceFun);
            return this;
        }

        /**
         * 加载器
         * @param loadFun
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> loadBy(Function<CMD, Optional<AGG>> loadFun){
            this.commandHandler.setAggLoader((cmd) -> loadFun.apply(cmd));
            return this;
        }

        /**
         * 业务逻辑执行器
         * @param updater
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> update(BiConsumer<AGG, CONTEXT> updater){
            this.commandHandler.addBizMethod(updater);
            return this;
        }

        /**
         * 创建后回调
         * @param updater
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> updateWhenCreate(BiConsumer<AGG, CONTEXT> updater){
            this.commandHandler.addWhenCreate(updater);
            return this;
        }


        /**
         * 成功后回调
         * @param onSuccessFun
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> onSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
            this.commandHandler.addOnSuccess(onSuccessFun);
            return this;
        }

        /**
         * 异常回调
         * @param errorFun
         * @return
         */
        public Syncer<AGG, CMD, CONTEXT> onError(BiConsumer<Exception, CONTEXT> errorFun){
            this.commandHandler.addOnError(errorFun);
            return this;
        }


        /**
         * 执行逻辑
         * @param cmd
         * @return
         */
        public AGG exe(CMD cmd) {
            this.commandHandler.validate();
            return this.commandHandler.handle(cmd);
        }

    }

}
