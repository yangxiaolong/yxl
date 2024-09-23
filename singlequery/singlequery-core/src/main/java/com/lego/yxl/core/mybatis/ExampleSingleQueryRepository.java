package com.lego.yxl.core.mybatis;


import com.lego.yxl.core.QueryIdRepository;
import com.lego.yxl.core.QueryObjectRepository;

public interface ExampleSingleQueryRepository<E, ID> extends QueryObjectRepository<E>, QueryIdRepository<E, ID> {
}
