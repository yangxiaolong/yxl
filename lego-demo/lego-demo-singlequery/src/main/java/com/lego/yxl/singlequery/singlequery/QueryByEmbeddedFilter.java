package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.EmbeddedFilter;
import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import lombok.Data;

@Data
public class QueryByEmbeddedFilter {
    @FieldGreaterThan("id")
    private Long id;

    @EmbeddedFilter
    private QueryByStatusAndBirth statusAndBirth;
}
