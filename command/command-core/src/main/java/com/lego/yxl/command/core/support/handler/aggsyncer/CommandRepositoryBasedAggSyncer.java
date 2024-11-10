package com.lego.yxl.command.core.support.handler.aggsyncer;


import com.lego.yxl.AggRoot;
import com.lego.yxl.command.core.CommandRepository;

public class CommandRepositoryBasedAggSyncer<AGG extends AggRoot<?>>
    implements AggSyncer<AGG> {
    private final CommandRepository<AGG, ?> repository;

    public CommandRepositoryBasedAggSyncer(CommandRepository<AGG, ?> repository) {
        this.repository = repository;
    }

    @Override
    public void sync(AGG agg) {
        this.repository.sync(agg);
    }
}
