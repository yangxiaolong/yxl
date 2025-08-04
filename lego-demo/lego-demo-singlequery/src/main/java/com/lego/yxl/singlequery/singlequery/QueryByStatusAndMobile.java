package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.annotation.FieldEqualTo;
import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import lombok.Data;

import java.util.Date;


@Data
public class QueryByStatusAndMobile {

    @FieldGreaterThan("birthAt")
    private Date birthAfter;

    @FieldEqualTo("status")
    private Integer status;

    @FieldEqualTo("mobile")
    private String mobile;
}
