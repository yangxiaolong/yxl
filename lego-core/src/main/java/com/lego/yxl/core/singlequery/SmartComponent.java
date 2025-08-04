package com.lego.yxl.core.singlequery;

public interface SmartComponent<D> {
    /**
     * 组件唯一标识
     * @return
     */
    default String id(){
        return getClass().getSimpleName();
    }

    /**
     * 是否能够处理
     * @param d
     * @return
     */
    boolean support(D d);
}
