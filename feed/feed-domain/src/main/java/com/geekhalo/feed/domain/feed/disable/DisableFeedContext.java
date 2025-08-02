package com.geekhalo.feed.domain.feed.disable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DisableFeedContext {
    private DisableFeedCommand command;

    private DisableFeedContext(DisableFeedCommand command){
         this.command = command;
    }

    public static DisableFeedContext apply(DisableFeedCommand command) {
        DisableFeedContext context = new DisableFeedContext(command);
        return context;
    }
}
