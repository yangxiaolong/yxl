package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldGreaterThan;
import com.lego.yxl.annotation.MaxResult;
import com.lego.yxl.annotation.MaxResultCheckStrategy;
import lombok.Data;


@Data
@MaxResult(max = 10, strategy = MaxResultCheckStrategy.SET_LIMIT)
public class QueryByIdGreaterWithMaxResult {
    @FieldGreaterThan(value = "id")
    private Long startUserId;
}
