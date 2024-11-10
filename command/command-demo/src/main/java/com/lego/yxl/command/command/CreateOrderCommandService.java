package com.lego.yxl.command.command;


import com.lego.yxl.command.core.CommandService;
import com.lego.yxl.command.core.CommandServiceDefinition;

@CommandServiceDefinition(domainClass = Order.class, repositoryClass = OrderRepositoryForCommand.class)
public interface CreateOrderCommandService
    extends CommandService<CreateOrderCommand, Order> {

    @Override
    Order execute(CreateOrderCommand cmd);
}
