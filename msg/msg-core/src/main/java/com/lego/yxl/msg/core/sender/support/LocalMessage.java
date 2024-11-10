package com.lego.yxl.msg.core.sender.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.msg.core.sender.Message;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


@Data
public class LocalMessage {
    private static final int MAX_RETRY_TIME = 3;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ERROR = 2;

    private Long id;

    private boolean orderly;

    private String topic;

    private String shardingKey;

    private String tag;

    private String msgKey;

    private String msgId;

    private String msg;

    private int retryTime = 0;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public static LocalMessage apply(Message message) {
        if (message == null){
            return null;
        }
        Preconditions.checkArgument(StringUtils.isNotEmpty(message.getTopic()));
        Preconditions.checkArgument(StringUtils.isNotEmpty(message.getMsg()));

        LocalMessage localMessage = new LocalMessage();
        localMessage.setOrderly(message.isOrderly());
        localMessage.setTopic(message.getTopic());
        localMessage.setShardingKey(message.getShardingKey());
        localMessage.setTag(message.getTag());
        localMessage.setMsg(message.getMsg());
        localMessage.setMsgKey(message.getMsgKey());

        localMessage.init();

        return localMessage;
    }

    private void init() {
        setStatus(STATUS_NONE);
        if (StringUtils.isEmpty(getShardingKey())){
            setShardingKey("");
        }
        if (StringUtils.isEmpty(getTag())){
            setTag("");
        }
        if (StringUtils.isEmpty(getMsgKey())){
            setMsgKey("");
        }

        setMsgId("");
        setCreateTime(new Date());
        setUpdateTime(new Date());
    }

    public boolean needRetry(Date now){
        return this.status != STATUS_SUCCESS //未成功
                && this.retryTime < MAX_RETRY_TIME //未达到最大尝试次数
                && now.getTime() - updateTime.getTime() > 10 * 1000; //更新时间在10秒外
    }

    public void sendSuccess(String msgId){
        this.setMsgId(msgId);
        this.setStatus(STATUS_SUCCESS);
        this.setUpdateTime(new Date());
    }

    public void sendError(){
        this.setStatus(STATUS_ERROR);
        this.retryTime = this.retryTime + 1;
        this.setUpdateTime(new Date());
    }

    public Message toMessage() {
        Message message = new Message();
        message.setOrderly(isOrderly());
        message.setTopic(getTopic());
        message.setShardingKey(getShardingKey());
        message.setTag(getTag());
        message.setMsg(getMsg());
        message.setMsgKey(getMsgKey());
        return message;
    }
}

