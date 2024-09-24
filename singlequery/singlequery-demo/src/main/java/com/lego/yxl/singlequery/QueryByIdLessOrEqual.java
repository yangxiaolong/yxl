package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldLessThanOrEqualTo;
import lombok.Data;


@Data
public class QueryByIdLessOrEqual {
    @FieldLessThanOrEqualTo("id")
    private Long maxId;
}
