package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldLessThan;
import lombok.Data;


@Data
public class QueryByIdLess {
    @FieldLessThan("id")
    private Long maxId;
}
