package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldNotEqualTo;
import lombok.Data;


@Data
public class QueryByNameNotEqual {
    @FieldNotEqualTo("name")
    private String name;
}
