package com.lego.yxl.command.demo.command;


import com.lego.yxl.command.core.CommandApplicationServiceDefinition;
import com.lego.yxl.command.core.web.AutoRegisterWebController;

@CommandApplicationServiceDefinition(
        domainClass = Order.class,
        repositoryClass = OrderRepositoryForCommand.class)
@AutoRegisterWebController(name = "order")
public interface OrderCommandApplicationServiceProxy extends OrderCommandApplicationService {
}
