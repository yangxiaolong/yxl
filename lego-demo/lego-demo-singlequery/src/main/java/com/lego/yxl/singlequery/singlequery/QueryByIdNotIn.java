package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldNotIn;
import lombok.Data;

import java.util.List;


@Data
public class QueryByIdNotIn {
    @FieldNotIn(value = "id")
    private List<Long> ids;
}
