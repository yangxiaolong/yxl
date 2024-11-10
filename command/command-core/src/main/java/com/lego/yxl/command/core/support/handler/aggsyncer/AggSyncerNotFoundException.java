package com.lego.yxl.command.core.support.handler.aggsyncer;

import lombok.Value;

@Value
public class AggSyncerNotFoundException extends RuntimeException{
    private final Class aggClass;
}
