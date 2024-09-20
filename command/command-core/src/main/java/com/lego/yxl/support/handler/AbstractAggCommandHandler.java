package com.lego.yxl.support.handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.lego.yxl.AggRoot;
import com.lego.yxl.ValidateService;
import com.lego.yxl.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.support.handler.aggsyncer.AggSyncer;
import com.lego.yxl.support.handler.contextfactory.ContextFactory;
import com.lego.yxl.support.handler.converter.ResultConverter;
import com.lego.yxl.support.handler.preaction.PreOperation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractAggCommandHandler<
        AGG extends AggRoot,
        CMD ,
        CONTEXT,
        RESULT> implements AggCommandHandler<CMD, RESULT>{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAggCommandHandler.class);

    // 懒加载代理工厂，对 Context 进行功能加强
    private final LazyLoadProxyFactory lazyLoadProxyFactory;
    // 验证器，为流程的不同阶段提供验证支持
    private final ValidateService validateService;

    // 内部领域事件分发器，用于发布领域事件
    private final ApplicationEventPublisher eventPublisher;

    // 事务管理器，为 null 时，不进行事务保护
    private final TransactionTemplate transactionTemplate;

    // 聚合同步器
    private AggSyncer<AGG> aggSyncer;

    // Context 工厂，用于将 Command 对象转换为 Context 对象
    private ContextFactory<CMD, CONTEXT> contextFactory;

    // 前置条件
    private final List<PreOperation> preOperations;

    // 结果转换器，从 Context 或 Agg 中提取信息，将其转换为返回结果
    private ResultConverter<AGG, CONTEXT, RESULT> resultConverter;

    // 业务处理链，可以同时执行多个业务
    private List<BiConsumer<AGG, CONTEXT>> bizMethods = Lists.newArrayList();


    // 默认的操作成功处理器，在操作成功后进行回调
    private BiConsumer<AGG, CONTEXT> successFun = (agg, context)->{
        LOGGER.info("success to handle {} and sync {} to DB", context, agg);
    };

    // 默认的异常处理器，在操作失败抛出异常时进行回调
    private BiConsumer<Exception, CONTEXT> errorFun = (exception, context) -> {
        LOGGER.error("failed to handle {}", context, exception);
        if (exception instanceof RuntimeException){
            throw (RuntimeException) exception;
        }
        throw new RuntimeException(exception);
    };


    protected AbstractAggCommandHandler(ValidateService validateService,
                                        LazyLoadProxyFactory lazyLoadProxyFactory,
                                        ApplicationEventPublisher eventPublisher,
                                        TransactionTemplate transactionTemplate,
                                        List<PreOperation> preOperations) {
        Preconditions.checkArgument(validateService != null);
        Preconditions.checkArgument(lazyLoadProxyFactory != null);
        Preconditions.checkArgument(eventPublisher != null);

        this.validateService = validateService;
        this.lazyLoadProxyFactory = lazyLoadProxyFactory;
        this.eventPublisher = eventPublisher;
        this.transactionTemplate = transactionTemplate;
        this.preOperations = preOperations;
    }


    /**
     * 顶层模版方法，用于控制主流程
     * @param cmd
     * @return
     */
    @Override
    public RESULT handle(CMD cmd) {
        CONTEXT contextToCache = null;
        try {
            // 1. 入参校验
            Preconditions.checkArgument(cmd != null);

            // 2. 参数验证，主要是格式验证
            validateForCommand(cmd);

            // 3. 将 Command 封装为 Context 对象
            CONTEXT context = createContext(cmd);
            contextToCache = context;

            // 4. 封装 Context 对象成 Proxy，使其具备高级特性
            //  a. 延迟加载能力
            //  b. Spring Bean 注入能力
            CONTEXT contextProxy = createProxy(context);
            contextToCache = contextProxy;

            // 5. 执行业务验证
            validateForContext(contextProxy);

            // 6. 准备前置条件
            callPreAction(contextProxy);

            AGG aggToUse = null;
            // 如有必要，开启事务保护
            if (this.transactionTemplate != null) {
                aggToUse = this.transactionTemplate.execute(status -> {
                    LOGGER.debug("Begin to Run In Transaction Template");

                    // 6. 创建或加载聚合根对象
                    AGG agg = getOrCreateAgg(cmd, contextProxy);

                    // 7.1
                    preCallBizMethod(agg, context);

                    // 7. 执行聚合根业务方法
                    callBizMethod(agg, contextProxy);

                    // 8. 执行通用规则校验
                    validateAfterBizMethod(agg, contextProxy);

                    // 9. 保存至数据库
                    syncToRepository(agg, contextProxy);

                    // 10. 发布领域事件
                    publishEvent(agg, contextProxy);
                    LOGGER.debug("End to Run In Transaction Template");
                    return agg;

                });
            }else {

                LOGGER.debug("Begin to Run Without Transaction Template");
                // 6. 创建或加载聚合根对象
                AGG agg = getOrCreateAgg(cmd, contextProxy);

                // 7. 执行聚合根业务方法
                callBizMethod(agg, contextProxy);

                // 8. 执行通用规则校验
                validateAfterBizMethod(agg, contextProxy);

                // 9. 保存至数据库
                syncToRepository(agg, contextProxy);

                // 10. 发布领域事件
                publishEvent(agg, contextProxy);
                aggToUse = agg;
                LOGGER.debug("End to Run Without Transaction Template");
            }

            RESULT result = convertToResult(aggToUse, contextProxy);

            this.successFun.accept(aggToUse, contextProxy);
            return result;
        }catch (Exception e){
            this.errorFun.accept(e, contextToCache);
        }
        return null;
    }

    private void preCallBizMethod(AGG agg, CONTEXT context) {

    }

    private void callPreAction(CONTEXT contextProxy) {
        if(this.preOperations != null){
            this.preOperations
                    .forEach(preOperation -> preOperation.action(contextProxy));
        }
    }

    /**
     * 验证器，对组件完整性进行验证
     */
    public void validate(){
        Preconditions.checkArgument(this.contextFactory != null, "Context Factory Can not be null");
        Preconditions.checkArgument(this.resultConverter != null, "Result Converter Can not be null");
        Preconditions.checkArgument(this.aggSyncer != null, "Agg Syncer Can not be null");
    }

    /**
     * 规则验证器，在执行业务方法后，验证规则避免
     * @param agg
     * @param context
     */
    protected void validateAfterBizMethod(AGG agg, CONTEXT context){
        this.validateService.validateRule(agg);
    }


    /**
     * Command 验证器，主要完成对入参的交易
     * @param cmd
     */
    protected void validateForCommand(CMD cmd) {
        this.validateService.validateParam(Collections.singletonList(cmd));
    }

    /**
     * 创建 Context 对象
     * @param param
     * @return
     */
    protected CONTEXT createContext(CMD param) {
        return this.contextFactory.create(param);
    }

    /**
     * 创建 Context 代理对象，进行功能加强
     * @param context
     * @return
     */
    protected CONTEXT createProxy(CONTEXT context){
        return this.lazyLoadProxyFactory.createProxyFor(context);
    }

    /**
     * 业务校验
     * @param context
     */
    protected void validateForContext(CONTEXT context) {
        this.validateService.validateBusiness(context);
    }

    /**
     * 获取或创建 聚合根对象
     * @param cmd
     * @param context
     * @return
     */
    protected abstract AGG getOrCreateAgg(CMD cmd, CONTEXT context);

    /**
     * 调用业务方法
     * @param agg
     * @param proxy
     */
    private void callBizMethod(AGG agg, CONTEXT proxy) {
        this.bizMethods.forEach(bizMethod -> bizMethod.accept(agg, proxy));
    }

    /**
     * 将聚合根同步到底层存储引擎
     * @param agg
     * @param context
     */
    protected void syncToRepository(AGG agg, CONTEXT context){
        this.aggSyncer.sync(agg);
    }

    /**
     * 发布领域事件
     * @param agg
     * @param context
     */
    protected void publishEvent(AGG agg, CONTEXT context) {
        agg.consumeAndClearEvent(event -> this.eventPublisher.publishEvent(event));
    }

    /**
     * 转换最后的执行结果
     * @param agg
     * @param proxy
     * @return
     */
    protected RESULT convertToResult(AGG agg, CONTEXT proxy) {
        return this.resultConverter.convert(agg, proxy);
    }

    /**
     * 添加业务调用
     * @param bizMethod
     */
    public void addBizMethod(BiConsumer<AGG, CONTEXT> bizMethod){
        this.bizMethods.add(bizMethod);
    }

    /**
     * 增加 成功处理器，链式模式，可以绑定多个处理器
     * @param onSuccessFun
     * @return
     */
    public void addOnSuccess(BiConsumer<AGG, CONTEXT>  onSuccessFun){
        Preconditions.checkArgument(onSuccessFun != null);
        this.successFun = onSuccessFun.andThen(this.successFun);
    }

    /**
     * 增加 异常处理器，链式模式，可以绑定多个处理器
     * @param errorFun
     * @return
     */
    public void addOnError(BiConsumer<Exception, CONTEXT>  errorFun){
        Preconditions.checkArgument(errorFun != null);
        this.errorFun = errorFun.andThen(this.errorFun);
    }

    /**
     * 设置聚合同步器
     * @param aggSyncer
     */
    public void setAggSyncer(AggSyncer<AGG> aggSyncer){
        Preconditions.checkArgument(aggSyncer != null);
        this.aggSyncer = aggSyncer;
    }

    /**
     * 设置 Context 工厂
     * @param contextFactory
     */
    public void setContextFactory(ContextFactory<CMD, CONTEXT> contextFactory) {
        Preconditions.checkArgument(contextFactory != null);
        this.contextFactory = contextFactory;
    }

    /**
     * 设置结果转换器
     * @param resultConverter
     */
    public void setResultConverter(ResultConverter<AGG, CONTEXT, RESULT> resultConverter) {
        Preconditions.checkArgument(resultConverter != null);
        this.resultConverter = resultConverter;
    }
}
