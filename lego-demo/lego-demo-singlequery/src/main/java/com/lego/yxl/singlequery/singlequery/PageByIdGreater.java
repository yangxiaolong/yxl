package com.lego.yxl.singlequery.singlequery;

import com.lego.yxl.core.singlequery.Pageable;
import com.lego.yxl.core.singlequery.Sort;
import com.lego.yxl.core.singlequery.annotation.FieldGreaterThan;
import lombok.Data;

@Data
public class PageByIdGreater {
    @FieldGreaterThan("id")
    private Long startId;

    private Pageable pageable;

    private Sort sort;
}
