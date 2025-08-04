package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldEqualTo;
import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import lombok.Data;

import java.util.Date;


@Data
public class QueryByStatusAndBirth {
    @FieldEqualTo("status")
    private Integer status;

    @FieldGreaterThan("birthAt")
    private Date birthAfter;
}
