package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldNotEqualTo;
import lombok.Data;


@Data
public class QueryByNameNotEqual {
    @FieldNotEqualTo("name")
    private String name;
}
