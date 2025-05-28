package com.lego.yxl.agg;

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
