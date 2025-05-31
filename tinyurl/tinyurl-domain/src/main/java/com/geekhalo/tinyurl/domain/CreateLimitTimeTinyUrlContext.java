package com.geekhalo.tinyurl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateLimitTimeTinyUrlContext
    extends AbstractCreateTinyUrlContext<CreateLimitTimeTinyUrlCommand>{

    public static CreateLimitTimeTinyUrlContext create(CreateLimitTimeTinyUrlCommand command){
        CreateLimitTimeTinyUrlContext context = new CreateLimitTimeTinyUrlContext();
        context.setCommand(command);
        return context;
    }
}
