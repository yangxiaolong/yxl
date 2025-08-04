package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldLessThanOrEqualTo;
import lombok.Data;


@Data
public class QueryByIdLessOrEqual {
    @FieldLessThanOrEqualTo("id")
    private Long maxId;
}
