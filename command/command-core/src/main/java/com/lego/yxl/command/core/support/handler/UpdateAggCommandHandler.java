package com.lego.yxl.command.core.support.handler;

import com.google.common.base.Preconditions;
import com.lego.yxl.command.core.support.handler.aggloader.AggLoader;
import com.lego.yxl.command.core.support.handler.preaction.PreOperation;
import com.lego.yxl.command.core.AggNotFoundException;
import com.lego.yxl.AggRoot;
import com.lego.yxl.validator.core.ValidateService;
import com.lego.yxl.loader.core.lazyloadproxyfactory.LazyLoadProxyFactory;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Setter
public class UpdateAggCommandHandler<
        AGG extends AggRoot,
        CMD,
        CONTEXT,
        RESULT>
        extends AbstractAggCommandHandler<AGG, CMD, CONTEXT, RESULT>{

    private AggLoader<CMD, AGG> aggLoader;

    // 聚合丢失处理器，聚合丢失时进行回调
    private Consumer<CONTEXT> onNotExistFun = context ->{
        throw new AggNotFoundException(context);
    };

    public UpdateAggCommandHandler(ValidateService validateService,
                                   List<PreOperation> preOperations,
                                   LazyLoadProxyFactory lazyLoadProxyFactory,
                                   ApplicationEventPublisher eventPublisher,
                                   TransactionTemplate transactionTemplate) {
        super(validateService, lazyLoadProxyFactory, eventPublisher, transactionTemplate, preOperations);
    }

    @Override
    protected AGG getOrCreateAgg(CMD cmd, CONTEXT context) {
        Optional<AGG> aggOptional = aggLoader.load(cmd);
        if (aggOptional.isPresent()){
            return aggOptional.get();
        }else {
            this.onNotExistFun.accept(context);
            return null;
        }
    }

    @Override
    public void validate(){
        super.validate();
        Preconditions.checkArgument(this.aggLoader != null, "Agg Loader Can not be null");
    }

    public void setAggLoader(AggLoader<CMD, AGG> aggLoader) {
        Preconditions.checkArgument(aggLoader != null);
        this.aggLoader = aggLoader;
    }

    public void addOnNotFound(Consumer<CONTEXT>  onNotExistFun){
        Preconditions.checkArgument(onNotExistFun != null);
        this.onNotExistFun = onNotExistFun.andThen(this.onNotExistFun);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\tUpdateCommand:").append("\n")
                .append("\t\t").append("ContextFactory:").append("\t").append(this.getContextFactory()).append("\n")
                .append("\t\t").append("AggLoader:").append("\t").append(this.aggLoader).append("\n")
                .append("\t\t").append("BizMethods:").append("\t").append(this.getBizMethods()).append("\n")
                .append("\t\t").append("AggSyncer:").append("\t").append(this.getAggSyncer()).append("\n")
                .append("\t\t").append("ResultConverter").append("\t").append(this.getResultConverter()).append("\n");
        return stringBuilder.toString();
    }
}
