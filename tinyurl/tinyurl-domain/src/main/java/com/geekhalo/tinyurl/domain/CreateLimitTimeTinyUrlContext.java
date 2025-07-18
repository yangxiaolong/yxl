package com.geekhalo.tinyurl.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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
