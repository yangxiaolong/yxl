package com.lego.yxl.core;

/**
 * Created by taoli on 2022/7/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 *
 */
public interface MaxResultConfigResolver {
    /**
     * 获取对象上最大结果配置
     * @param query
     * @return
     */
    MaxResultConfig maxResult(Object query);
}
