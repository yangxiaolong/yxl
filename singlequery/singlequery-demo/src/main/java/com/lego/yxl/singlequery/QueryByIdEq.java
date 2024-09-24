package com.lego.yxl.singlequery;

import com.lego.yxl.annotation.FieldEqualTo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueryByIdEq {
    @FieldEqualTo("id")
    @NotNull
    private Long id;
}
