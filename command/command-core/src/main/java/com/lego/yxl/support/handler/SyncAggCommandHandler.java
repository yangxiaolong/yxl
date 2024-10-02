package com.lego.yxl.support.handler;

import com.google.common.base.Preconditions;
import com.lego.yxl.AggRoot;
import com.lego.yxl.core.ValidateService;
import com.lego.yxl.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.support.AbstractEntity;
import com.lego.yxl.support.handler.aggfactory.AggFactory;
import com.lego.yxl.support.handler.aggloader.AggLoader;
import com.lego.yxl.support.handler.preaction.PreOperation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class SyncAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggFactory<CONTEXT, AGG> aggFactory;

    private AggLoader<CMD, AGG> aggLoader;

    private BiConsumer<AGG, CONTEXT> updaterWhenCreate = (a, context) ->{
        if (a instanceof AbstractEntity){
            ((AbstractEntity)a).prePersist();
        }
    };

    public SyncAggCommandHandler(ValidateService validateService,
                                 List<PreOperation> preOperations,
                                 LazyLoadProxyFactory lazyLoadProxyFactory,
                                 ApplicationEventPublisher eventPublisher,
                                 TransactionTemplate transactionTemplate) {
        super(validateService, lazyLoadProxyFactory, eventPublisher, transactionTemplate, preOperations);
    }

    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        try {
            Optional<AGG> aggOptional = aggLoader.load(cmd);
            if (aggOptional.isPresent()){
                return aggOptional.get();
            }else {
                AGG agg = this.aggFactory.create(context);

                updaterWhenCreate.accept(agg, context);

                return agg;
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validate(){
        super.validate();
        Preconditions.checkArgument(this.aggFactory != null, "Agg Factory Can not be null");
        Preconditions.checkArgument(this.aggLoader != null, "Agg Loader Can not be null");
    }

    public void addWhenCreate(BiConsumer<AGG, CONTEXT> updater){
        Preconditions.checkArgument(updater != null);
        this.updaterWhenCreate = updater.andThen(this.updaterWhenCreate);
    }

    public void setAggFactory(AggFactory<CONTEXT, AGG> aggFactory) {
        Preconditions.checkArgument(aggFactory != null);
        this.aggFactory = aggFactory;
    }



    public void setAggLoader(AggLoader<CMD, AGG> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tSyncCommand:\n")
                .append("\t\t").append("ContextFactory:").append("\t").append(this.getContextFactory()).append("\n")
                .append("\t\t").append("AggFactory:").append("\t").append(this.aggFactory).append("\n")
                .append("\t\t").append("AggLoader:").append("\t").append(this.aggLoader).append("\n")
                .append("\t\t").append("BizMethods:").append("\t").append(this.getBizMethods()).append("\n")
                .append("\t\t").append("AggSyncer:").append("\t").append(this.getAggSyncer()).append("\n")
                .append("\t\t").append("ResultConverter").append("\t").append(this.getResultConverter()).append("\n");
        return stringBuilder.toString();
    }


}
