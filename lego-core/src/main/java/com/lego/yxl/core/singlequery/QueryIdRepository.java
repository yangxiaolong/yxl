package com.lego.yxl.core.singlequery;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface QueryIdRepository<E, ID> {
    default <R> R getById(ID id, Function<E, R> converter){
        E e = getById(id);
        if (e == null){
            return null;
        }
        return converter.apply(e);
    }

    E getById(ID id);

    default <R> List<R> getByIds(List<ID> ids, Function<E, R> converter){
        List<E> entities = getByIds(ids);
        if (CollectionUtils.isEmpty(entities)){
            return Collections.emptyList();
        }

        return entities.stream()
                .filter(Objects::nonNull)
                .map(converter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<E> getByIds(List<ID> ids);
}
