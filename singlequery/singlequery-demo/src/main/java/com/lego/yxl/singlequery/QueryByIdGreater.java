package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldGreaterThan;
import lombok.Data;


@Data
public class QueryByIdGreater {
    @FieldGreaterThan(value = "id")
    private Long startUserId;
}
