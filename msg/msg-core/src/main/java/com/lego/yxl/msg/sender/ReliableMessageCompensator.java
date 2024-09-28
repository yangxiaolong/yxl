package com.lego.yxl.msg.sender;

import java.util.Date;


public interface ReliableMessageCompensator {
    void compensate(Date startDate, int sizePreTask);
}
