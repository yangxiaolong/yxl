package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.*;
import com.lego.yxl.command.core.CommandApplicationServiceDefinition;

@CommandApplicationServiceDefinition(
        repositoryClass = TinyUrlCommandRepository.class,
        domainClass = TinyUrl.class
)
public interface TinyUrlCommandApplicationService extends CustomTinyUrlCommandApplicationService {

    TinyUrl createTinyUrl(CreateTinyUrlCommand command);

    TinyUrl createExpireTimeTinyUrl(CreateExpireTimeTinyUrlCommand command);

    TinyUrl createLimitTimeTinyUrl(CreateLimitTimeTinyUrlCommand command);

    void disableTinyUrl(DisableTinyUrlCommand command);

}
