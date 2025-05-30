package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.google.common.base.Preconditions;
import com.lego.yxl.command.core.support.AbstractAggRoot;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractTargetCount extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ActionTarget target;

    @Column(name = "count")
    private Long count;

    protected void init(ActionTarget target, long count) {
        Preconditions.checkArgument(target != null);
        setTarget(target);
        setCount(count);
    }
}
