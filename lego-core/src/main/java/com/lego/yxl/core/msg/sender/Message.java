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
    private boolean orderly;// 是否顺序消息

    private String topic;

    private String shardingKey;// 顺序消息的队列选择键

    private String msgKey;// 业务唯一键，用于查询/幂等

    private String tag;

    private String msg;// 消息体
}
