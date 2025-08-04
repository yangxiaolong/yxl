package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldIn;
import lombok.Data;

import java.util.List;


@Data
public class QueryByIdIn {
    @FieldIn(value = "id", fieldType = Long.class)
    private List<Long> ids;
}
