package com.lego.yxl.core.msg.sender;

import java.util.Date;


public interface ReliableMessageCompensator {
    void compensate(Date startDate, int sizePreTask);
}
