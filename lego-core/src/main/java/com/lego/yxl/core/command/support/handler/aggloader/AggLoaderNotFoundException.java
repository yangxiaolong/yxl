package com.lego.yxl.core.command.support.handler.aggloader;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AggLoaderNotFoundException extends RuntimeException{
    private final Class cmdClass;
    private final Class aggClass;
}
