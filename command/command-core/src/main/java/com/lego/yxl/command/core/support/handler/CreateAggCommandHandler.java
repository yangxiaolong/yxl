package com.lego.yxl.command.core.support.handler;

import com.google.common.base.Preconditions;
import com.lego.yxl.AggRoot;
import com.lego.yxl.command.core.support.handler.aggfactory.AggFactory;
import com.lego.yxl.command.core.support.handler.preaction.PreOperation;
import com.lego.yxl.validator.core.ValidateService;
import com.lego.yxl.loader.core.lazyloadproxyfactory.LazyLoadProxyFactory;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Setter
public class CreateAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggFactory<CONTEXT, AGG> aggFactory;

    public CreateAggCommandHandler(ValidateService validateService,
                                   LazyLoadProxyFactory lazyLoadProxyFactory,
                                   ApplicationEventPublisher eventPublisher,
                                   TransactionTemplate transactionTemplate, List<PreOperation> preOperations) {
        super(validateService, lazyLoadProxyFactory, eventPublisher, transactionTemplate, preOperations);
    }


    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        return this.aggFactory.create(context);
    }

    @Override
    public void validate(){
        super.validate();
        Preconditions.checkArgument(this.aggFactory != null, "Agg Factory Can not be null");
    }

    public void setAggFactory(AggFactory<CONTEXT, AGG> aggFactory) {
        Preconditions.checkArgument(aggFactory != null);
        this.aggFactory = aggFactory;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tCreateCommand:\n")
                .append("\t\t").append("ContextFactory:").append("\t").append(this.getContextFactory()).append("\n")
                .append("\t\t").append("AggFactory:").append("\t").append(this.aggFactory).append("\n")
                .append("\t\t").append("BizMethods:").append("\t").append(this.getBizMethods()).append("\n")
                .append("\t\t").append("AggSyncer:").append("\t").append(this.getAggSyncer()).append("\n")
                .append("\t\t").append("ResultConverter").append("\t").append(this.getResultConverter()).append("\n");
        return stringBuilder.toString();
    }

}
