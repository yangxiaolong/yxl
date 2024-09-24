package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldIsNull;
import lombok.Data;


@Data
public class QueryByNameIsNull {
    @FieldIsNull("name")
    private boolean isNull;
}
