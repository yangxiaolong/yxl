package com.geekhalo.tinyurl.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateLimitTimeTinyUrlCommand extends AbstractCreateTinyUrlCommand{
    @NotNull
    private Integer maxCount;

}
