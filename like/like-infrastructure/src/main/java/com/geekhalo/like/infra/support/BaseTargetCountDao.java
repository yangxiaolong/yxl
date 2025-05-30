package com.geekhalo.like.infra.support;

import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.target.ActionTarget;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BaseTargetCountDao<C extends AbstractTargetCount> {

    @Query("select c from " +
            "#{#entityName} c " +
            "WHERE c.target.id = :targetId AND c.target.type = :targetType")
    Optional<C> getByTarget(@Param("targetType") String targetType,
                            @Param("targetId") Long targetId);

    default Optional<C> getByTarget(ActionTarget target){
        return getByTarget(target.getType(), target.getId());
    }

    List<C> getByTargetTypeAndTargetIdIn(String type, List<Long> targetIds);

    @Transactional(readOnly = false)
    default void incr(ActionTarget target, long count){
        incr(target.getType(), target.getId(), count);
    }

    @Modifying
    @Query("UPDATE #{#entityName} c " +
            "SET c.count = c.count + :count " +
            "WHERE c.target.id = :targetId AND c.target.type = :targetType")
    void incr(@Param("targetType") String targetType,
              @Param("targetId") Long targetId,
              @Param("count") Long count);
}
