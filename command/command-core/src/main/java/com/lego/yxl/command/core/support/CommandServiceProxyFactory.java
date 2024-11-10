package com.lego.yxl.command.core.support;

import com.lego.yxl.command.core.NoCommandService;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Setter(AccessLevel.PRIVATE)
@Slf4j
public class CommandServiceProxyFactory extends BaseCommandProxyFactory {

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected CommandServiceMetadata buildMetadata(Class commandService) {
        return CommandServiceMetadata.fromCommandService(commandService);
    }

    @Override
    protected Class getSkipAnnotation() {
        return NoCommandService.class;
    }

    public <T> T createCommandApplicationService(Class commandService){
        return super.createProxy(commandService);
    }

}
