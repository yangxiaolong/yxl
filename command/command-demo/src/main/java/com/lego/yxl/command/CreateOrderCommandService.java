package com.lego.yxl.command;


import com.lego.yxl.CommandService;
import com.lego.yxl.CommandServiceDefinition;

@CommandServiceDefinition(domainClass = Order.class, repositoryClass = OrderRepositoryForCommand.class)
public interface CreateOrderCommandService
    extends CommandService<CreateOrderCommand, Order> {

    @Override
    Order execute(CreateOrderCommand cmd);
}
