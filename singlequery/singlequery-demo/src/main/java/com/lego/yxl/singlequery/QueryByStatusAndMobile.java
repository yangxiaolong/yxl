package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldEqualTo;
import com.lego.yxl.annotation.FieldGreaterThan;
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
