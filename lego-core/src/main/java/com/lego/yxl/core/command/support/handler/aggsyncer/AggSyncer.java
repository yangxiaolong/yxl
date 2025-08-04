package com.lego.yxl.core.command.support.handler.aggsyncer;

import com.lego.yxl.core.command.support.AggRoot;

public interface AggSyncer<AGG extends AggRoot> {
    void sync(AGG agg);
}
