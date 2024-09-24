package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.EmbeddedFilter;
import com.lego.yxl.annotation.FieldGreaterThan;
import lombok.Data;

@Data
public class QueryByEmbeddedFilter {
    @FieldGreaterThan("id")
    private Long id;

    @EmbeddedFilter
    private QueryByStatusAndBirth statusAndBirth;
}
