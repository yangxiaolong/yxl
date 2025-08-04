package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldGreaterThanOrEqualTo;
import lombok.Data;

@Data
public class QueryByIdGreaterOrEquals {
    @FieldGreaterThanOrEqualTo(value = "id")
    private Long startUserId;
}
