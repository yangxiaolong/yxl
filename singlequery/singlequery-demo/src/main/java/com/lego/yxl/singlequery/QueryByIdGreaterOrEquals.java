package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldGreaterThanOrEqualTo;
import lombok.Data;

@Data
public class QueryByIdGreaterOrEquals {
    @FieldGreaterThanOrEqualTo(value = "id")
    private Long startUserId;
}
