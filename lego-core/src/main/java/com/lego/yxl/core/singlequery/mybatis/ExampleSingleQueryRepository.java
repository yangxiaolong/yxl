package com.lego.yxl.core.singlequery.mybatis;


import com.lego.yxl.core.singlequery.QueryIdRepository;
import com.lego.yxl.core.singlequery.QueryObjectRepository;

public interface ExampleSingleQueryRepository<E, ID> extends QueryObjectRepository<E>, QueryIdRepository<E, ID> {
}
