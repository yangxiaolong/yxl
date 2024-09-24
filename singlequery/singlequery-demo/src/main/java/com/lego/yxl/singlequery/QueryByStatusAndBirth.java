package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldEqualTo;
import com.lego.yxl.annotation.FieldGreaterThan;
import lombok.Data;

import java.util.Date;


@Data
public class QueryByStatusAndBirth {
    @FieldEqualTo("status")
    private Integer status;

    @FieldGreaterThan("birthAt")
    private Date birthAfter;
}
