package com.geekhalo.like.domain.target;

public interface ActionTargetLoader {
    ActionTarget loadByTarget(String type, Long id);
}
