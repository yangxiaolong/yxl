package com.geekhalo.like.domain.target;

public interface SingleActionTargetLoader {
    /**
     * 是否支持 type 类型的 Target
     * @param type
     * @return
     */
    boolean support(String type);

    /**
     * 加载 Target 对象
     * @param type
     * @param id
     * @return
     */
    ActionTarget load(String type, Long id);
}
