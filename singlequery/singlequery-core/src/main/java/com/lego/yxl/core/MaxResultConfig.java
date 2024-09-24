package com.lego.yxl.core;

import com.lego.yxl.annotation.MaxResultCheckStrategy;
import lombok.Builder;
import lombok.Value;

/**
 * 最大结果配置
 */
@Value
@Builder
public class MaxResultConfig {
    /**
     * 允许的最大结果数
     */
    private final int maxResult;

    /**
     * 结果检测策略
     */
    private final MaxResultCheckStrategy checkStrategy;
}
