package com.geekhalo.tinyurl.app;

import com.geekhalo.tinyurl.domain.TinyUrl;
import lombok.Value;

@Value
public class TinyUrlAccessedEvent {
    private TinyUrl tinyUrl;
}
