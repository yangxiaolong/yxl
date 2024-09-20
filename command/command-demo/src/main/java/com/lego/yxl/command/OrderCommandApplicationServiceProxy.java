package com.lego.yxl.command;


import com.lego.yxl.CommandApplicationServiceDefinition;
import com.lego.yxl.web.AutoRegisterWebController;

@CommandApplicationServiceDefinition(
        domainClass = Order.class,
        repositoryClass = OrderRepositoryForCommand.class)
@AutoRegisterWebController(name = "order")
public interface OrderCommandApplicationServiceProxy extends OrderCommandApplicationService {
}
