package com.lego.yxl.core.singlequery;

public interface MaxResultConfigResolver {
    /**
     * 获取对象上最大结果配置
     * @param query
     * @return
     */
    MaxResultConfig maxResult(Object query);
}
