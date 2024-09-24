package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldGreaterThan;
import com.lego.yxl.core.Pageable;
import com.lego.yxl.core.Sort;
import lombok.Data;


@Data
public class PageByIdGreater {
    @FieldGreaterThan("id")
    private Long startId;

    private Pageable pageable;

    private Sort sort;
}
