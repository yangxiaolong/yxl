package com.lego.yxl.command.command;

import com.lego.yxl.core.command.CommandApplicationServiceDefinition;
import com.lego.yxl.core.command.web.AutoRegisterWebController;

@CommandApplicationServiceDefinition(
        domainClass = Order.class,
        repositoryClass = OrderRepositoryForCommand.class)
@AutoRegisterWebController(name = "order")
public interface OrderCommandApplicationServiceProxy extends OrderCommandApplicationService {
}
