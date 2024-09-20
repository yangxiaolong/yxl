package com.lego.yxl.command;

import com.lego.yxl.NoCommandApplicationService;

@NoCommandApplicationService
public interface OrderCommandApplicationService extends CustomOrderCommandService{

    Order create(CreateOrderCommand command);

    void paySuccess(PayByIdSuccessCommand command);

    Order syncByOrderId(SyncOrderByIdCommand command);
}
