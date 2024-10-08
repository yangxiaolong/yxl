package com.lego.yxl.core;

public interface QueryConverter<E> {
    E convertForQuery(Object query);

    E convertForCount(Object query);

    Pageable findPageable(Object query);

    Sort findSort(Object query);

    void validate(Class cls);
}
