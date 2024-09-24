package com.lego.yxl.core.jpa;

import com.lego.yxl.core.QueryConverter;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationConverter<E>
        extends QueryConverter<Specification<E>> {
}
