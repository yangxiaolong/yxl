package com.lego.yxl.core.command.support;

import java.util.Date;

public interface DomainEvent<ID, AGG extends AggRoot<ID>> {

    Date createAt();

    AGG source();

    default AGG getSource(){
        return source();
    }

    default Date getCreateTime(){
        return createAt();
    }
}
