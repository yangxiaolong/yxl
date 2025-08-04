package com.lego.yxl.core.msg.sender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private boolean orderly;

    private String topic;

    private String shardingKey;

    private String msgKey;

    private String tag;

    private String msg;
}
