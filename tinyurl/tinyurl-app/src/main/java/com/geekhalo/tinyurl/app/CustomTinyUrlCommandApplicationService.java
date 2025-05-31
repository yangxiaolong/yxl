package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.IncrAccessCountCommand;

public interface CustomTinyUrlCommandApplicationService {
    void incrAccessCount(IncrAccessCountCommand command);
}
