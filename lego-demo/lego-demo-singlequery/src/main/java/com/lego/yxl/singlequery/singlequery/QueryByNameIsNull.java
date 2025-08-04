package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldIsNull;
import lombok.Data;


@Data
public class QueryByNameIsNull {
    @FieldIsNull("name")
    private boolean isNull;
}
