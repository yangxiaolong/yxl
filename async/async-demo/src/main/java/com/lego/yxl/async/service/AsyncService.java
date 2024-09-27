package com.lego.yxl.async.service;

import com.lego.yxl.async.annotation.AsyncBasedRocketMQ;
import com.lego.yxl.async.annotation.AsyncForOrderedBasedRocketMQ;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AsyncService {
    @Getter
    private final List<CallData> callDatas = new ArrayList<>();

    @AsyncBasedRocketMQ(topic = "${async.test.normal.topic}",
            tag = "asyncTest1",
            consumerGroup = "${async.test.normal.group1}")
    public void asyncTest1(Long id, String name, AsyncInputBean bean) {
        log.info("receive data id {}, name {}, bean {}", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }


    @AsyncBasedRocketMQ(topic = "${async.test.normal.topic}",
            tag = "asyncTest2",
            consumerGroup = "${async.test.normal.group2}")
    public void asyncTest2(Long id, String name, AsyncInputBean bean) {
        log.info("receive data id {}, name {}, bean {}", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }


    @AsyncForOrderedBasedRocketMQ(topic = "${async.test.order.topic}",
            tag = "asyncTest1",
            shardingKey = "#id",
            consumerGroup = "${async.test.order.group1}")
    public void asyncTestForOrder1(Long id, String name, AsyncInputBean bean) {
        log.info("receive data id {}, name {}, bean {}", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }

    @AsyncForOrderedBasedRocketMQ(topic = "${async.test.order.topic}",
            tag = "asyncTest2",
            shardingKey = "#id",
            consumerGroup = "${async.test.order.group2}")
    public void asyncTestForOrder2(Long id, String name, AsyncInputBean bean) {
        log.info("receive data id {}, name {}, bean {}", id, name, bean);

        CallData callData = new CallData(id, name, bean);
        this.callDatas.add(callData);
    }


    @Value
    public static class CallData {
        private final Long id;
        private final String name;
        private final AsyncInputBean bean;
    }
}
