package com.lego.yxl.support.handler.aggsyncer;

import com.lego.yxl.AggRoot;

public interface AggSyncer<AGG extends AggRoot> {
    void sync(AGG agg);
}
