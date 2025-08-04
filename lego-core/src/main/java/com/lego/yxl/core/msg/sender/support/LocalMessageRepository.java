package com.lego.yxl.core.msg.sender.support;

import java.util.Date;
import java.util.List;


public interface LocalMessageRepository {
    void save(LocalMessage message);

    void update(LocalMessage message);

    List<LocalMessage> loadNotSuccessByUpdateGt(Date latestUpdateTime, int size);
}