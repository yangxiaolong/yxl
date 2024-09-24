package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldLessThan;
import lombok.Data;


@Data
public class QueryByIdLess {
    @FieldLessThan("id")
    private Long maxId;
}
