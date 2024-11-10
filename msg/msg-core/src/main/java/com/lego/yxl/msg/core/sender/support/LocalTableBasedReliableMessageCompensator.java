package com.lego.yxl.msg.core.sender.support;


import com.lego.yxl.msg.core.sender.ReliableMessageCompensator;

import java.util.Date;


public class LocalTableBasedReliableMessageCompensator implements ReliableMessageCompensator {
    private final ReliableMessageSendService reliableMessageSendService;

    public LocalTableBasedReliableMessageCompensator(ReliableMessageSendService reliableMessageSendService) {
        this.reliableMessageSendService = reliableMessageSendService;
    }

    @Override
    public void compensate(Date startDate, int sizePreTask) {
        this.reliableMessageSendService.loadAndResend(startDate, sizePreTask);
    }
}
