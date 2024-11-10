package com.lego.yxl.joininmemory.core.service;

import java.util.Collections;
import java.util.List;

/**
 */
public interface JoinService {
    /**
     * 执行内存 join
     *
     * @param t
     */
    default <T> void joinInMemory(T t) {
        if (t == null) {
            return;
        }
        joinInMemory((Class<T>) t.getClass(), Collections.singletonList(t));
    }

    default <T> void joinInMemory(List<T> t) {
        if (t == null || t.isEmpty()) {
            return;
        }
        if (t.size() == 1) {
            joinInMemory(t.get(0));
        } else {
            joinInMemory((Class<T>) t.get(0).getClass(), t);
        }
    }

    /**
     * 执行内存 Join
     *
     * @param tCls 实际类型
     * @param t    需要抓取的集合
     */
    <T> void joinInMemory(Class<T> tCls, List<T> t);

    /**
     * 注册一个类型，主要用于初始化
     *
     * @param tCls
     * @param <T>
     */
    <T> void register(Class<T> tCls);

}
