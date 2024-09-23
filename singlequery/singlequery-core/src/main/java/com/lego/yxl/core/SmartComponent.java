package com.lego.yxl.core;

/**
 * Created by taoli on 2022/8/7.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
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
