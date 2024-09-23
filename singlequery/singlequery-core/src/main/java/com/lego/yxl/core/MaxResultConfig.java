package com.lego.yxl.core;

import com.lego.yxl.annotation.MaxResultCheckStrategy;
import lombok.Builder;
import lombok.Value;

/**
 * Created by taoli on 2022/7/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
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
