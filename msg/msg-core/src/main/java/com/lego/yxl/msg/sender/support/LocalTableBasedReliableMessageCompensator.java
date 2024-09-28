package com.lego.yxl.msg.sender.support;


import com.lego.yxl.msg.sender.ReliableMessageCompensator;

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
