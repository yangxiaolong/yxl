package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldNotIn;
import lombok.Data;

import java.util.List;


@Data
public class QueryByIdNotIn {
    @FieldNotIn(value = "id")
    private List<Long> ids;
}
