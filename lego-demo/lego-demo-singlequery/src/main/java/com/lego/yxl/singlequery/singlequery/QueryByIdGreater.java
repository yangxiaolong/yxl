package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import lombok.Data;


@Data
public class QueryByIdGreater {
    @FieldGreaterThan(value = "id")
    private Long startUserId;
}
