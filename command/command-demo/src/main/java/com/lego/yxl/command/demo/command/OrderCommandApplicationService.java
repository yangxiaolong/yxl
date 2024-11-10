package com.lego.yxl.command.demo.command;

import com.lego.yxl.command.core.NoCommandApplicationService;

@NoCommandApplicationService
public interface OrderCommandApplicationService extends CustomOrderCommandService{

    Order create(CreateOrderCommand command);

    void paySuccess(PayByIdSuccessCommand command);

    Order syncByOrderId(SyncOrderByIdCommand command);
}
