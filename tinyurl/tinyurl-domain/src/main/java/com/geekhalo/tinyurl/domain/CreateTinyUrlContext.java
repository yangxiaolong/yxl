package com.geekhalo.tinyurl.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CreateTinyUrlContext extends AbstractCreateTinyUrlContext<CreateTinyUrlCommand> {


    public static CreateTinyUrlContext create(CreateTinyUrlCommand command) {
        CreateTinyUrlContext context = new CreateTinyUrlContext();
        context.setCommand(command);
        return context;
    }
}
