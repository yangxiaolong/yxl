package com.lego.yxl.core.singlequery.jpa;

import com.lego.yxl.core.singlequery.QueryConverter;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationConverter<E>
        extends QueryConverter<Specification<E>> {
}
