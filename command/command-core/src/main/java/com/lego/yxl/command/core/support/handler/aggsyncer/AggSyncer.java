package com.lego.yxl.command.core.support.handler.aggsyncer;

import com.lego.yxl.AggRoot;

public interface AggSyncer<AGG extends AggRoot> {
    void sync(AGG agg);
}