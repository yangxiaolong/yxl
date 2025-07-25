package com.geekhalo.tinyurl.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IncrAccessCountCommand extends AbstractUpdateTinyUrlCommand {
    private Integer incrCount;

    public int incrCount(){
        return incrCount == null ? 1 : incrCount;
    }
}
