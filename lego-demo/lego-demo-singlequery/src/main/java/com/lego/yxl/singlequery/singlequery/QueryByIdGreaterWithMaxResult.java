package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import com.lego.yxl.core.singlequery.annotation.MaxResult;
import com.lego.yxl.core.singlequery.annotation.MaxResultCheckStrategy;
import lombok.Data;


@Data
@MaxResult(max = 10, strategy = MaxResultCheckStrategy.SET_LIMIT)
public class QueryByIdGreaterWithMaxResult {
    @FieldGreaterThan(value = "id")
    private Long startUserId;
}
