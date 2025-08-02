package com.geekhalo.feed.domain.feed.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateFeedContext{
    private CreateFeedCommand command;

    private CreateFeedContext(CreateFeedCommand command){
         this.command = command;
    }

    public static CreateFeedContext apply(CreateFeedCommand command) {
        CreateFeedContext context = new CreateFeedContext(command);
        return context;
    }
}
