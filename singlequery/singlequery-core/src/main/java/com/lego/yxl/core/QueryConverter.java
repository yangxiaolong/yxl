package com.lego.yxl.core;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface QueryConverter<E> {
    E convertForQuery(Object query);

    E convertForCount(Object query);

    Pageable findPageable(Object query);

    Sort findSort(Object query);

    void validate(Class cls);
}
