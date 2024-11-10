package com.lego.yxl.msg.core.sender;

import java.util.Date;


public interface ReliableMessageCompensator {
    void compensate(Date startDate, int sizePreTask);
}
