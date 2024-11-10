package com.lego.yxl.command.core.support.handler.aggsyncer;

import com.lego.yxl.AggRoot;

public interface SmartAggSyncer <AGG extends AggRoot> extends AggSyncer<AGG> {
    boolean apply(Class aggClass);
}
