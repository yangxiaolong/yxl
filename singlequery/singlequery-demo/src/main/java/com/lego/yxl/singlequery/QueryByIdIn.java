package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldIn;
import lombok.Data;

import java.util.List;


@Data
public class QueryByIdIn {
    @FieldIn(value = "id", fieldType = Long.class)
    private List<Long> ids;
}
