package com.geekhalo.feed.domain.feed.enable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnableFeedContext {
    private EnableFeedCommand command;

    private EnableFeedContext(EnableFeedCommand command) {
        this.command = command;
    }

    public static EnableFeedContext apply(EnableFeedCommand command) {
        return new EnableFeedContext(command);
    }

}
