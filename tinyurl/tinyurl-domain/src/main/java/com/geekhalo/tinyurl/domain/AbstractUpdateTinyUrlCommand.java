package com.geekhalo.tinyurl.domain;

import com.lego.yxl.command.core.CommandForUpdateById;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class AbstractUpdateTinyUrlCommand implements CommandForUpdateById<Long> {
    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
}
