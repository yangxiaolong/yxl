package com.geekhalo.like.domain.target;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(Integer.MAX_VALUE)
public class NullSingleActionTargetLoader implements SingleActionTargetLoader{
    @Override
    public boolean support(String type) {
        return true;
    }

    @Override
    public ActionTarget load(String type, Long id) {
        return ActionTarget.apply(type, id);
    }
}
