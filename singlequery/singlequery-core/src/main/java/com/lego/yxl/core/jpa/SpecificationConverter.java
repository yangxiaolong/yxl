package com.lego.yxl.core.jpa;

import com.lego.yxl.core.QueryConverter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by taoli on 2022/8/30.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface SpecificationConverter<E>
        extends QueryConverter<Specification<E>> {
}
