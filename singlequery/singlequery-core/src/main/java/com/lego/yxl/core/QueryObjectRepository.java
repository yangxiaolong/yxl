package com.lego.yxl.core;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/9/24.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface QueryObjectRepository<E> {
    /**
     * 是否为有效的 查询对象
     *
     * @param cls
     */
    void checkForQueryObject(Class cls);

    /**
     * @param query     查询参数
     * @param converter 结果转化器
     * @param <Q>
     * @param <R>
     * @return
     */
    default <Q, R> List<R> listOf(Q query, Function<E, R> converter) {
        List<E> entities = listOf(query);
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    <Q> List<E> listOf(Q query);

    /**
     * 获取数量
     *
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    <Q> Long countOf(Q query);

    /**
     * 获取单条记录
     *
     * @param query     查询参数
     * @param converter 结果转化器
     * @param <Q>
     * @param <R>
     * @return
     */
    default <Q, R> R get(Q query, Function<E, R> converter) {
        E entity = get(query);
        if (entity == null) {
            return null;
        }
        return converter.apply(entity);
    }

    /**
     * 获取单条记录
     *
     * @param query 查询参数
     * @param <Q>
     * @return
     */
    <Q> E get(Q query);

    /**
     * 分页查询
     *
     * @param query     查询参数
     * @param converter 结果转换器
     * @param <Q>
     * @param <R>
     * @return
     */
    default <Q, R> Page<R> pageOf(Q query, Function<E, R> converter) {
        Page<E> entityPage = this.pageOf(query);
        if (entityPage == null) {
            return null;
        }
        return entityPage.convert(converter);
    }

    /**
     * 分页查询
     *
     * @param query
     * @param <Q>
     * @return
     */
    <Q> Page<E> pageOf(Q query);
}
