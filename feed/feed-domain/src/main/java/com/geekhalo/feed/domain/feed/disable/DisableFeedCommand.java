package com.geekhalo.feed.domain.feed.disable;

import com.lego.yxl.core.command.CommandForUpdateById;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableFeedCommand implements CommandForUpdateById<Long> {

    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }
}
