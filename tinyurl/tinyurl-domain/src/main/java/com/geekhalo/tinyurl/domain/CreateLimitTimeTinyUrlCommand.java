package com.geekhalo.tinyurl.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLimitTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    @NotNull
    private Integer maxCount;

}
