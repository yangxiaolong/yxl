package com.lego.yxl.async.demo.controller;

import com.lego.yxl.async.demo.service.AsyncInputBean;
import com.lego.yxl.async.demo.service.AsyncService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/async")
@Slf4j
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("/asyncTest1")
    public void asyncTest1() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            asyncService.getCallDatas().clear();

            Long id = RandomUtils.nextLong();
            String name = String.valueOf(RandomUtils.nextLong());
            AsyncInputBean bean = createAsyncInputBean();
            asyncService.asyncTest1(id, name, bean);

            {
                List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
                Assertions.assertTrue(CollectionUtils.isEmpty(callDatas));
            }

            TimeUnit.SECONDS.sleep(2);

            {
                List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
                Assertions.assertFalse(CollectionUtils.isEmpty(callDatas));

                AsyncService.CallData callData = callDatas.get(0);
                Assertions.assertEquals(id, callData.getId());
                Assertions.assertEquals(name, callData.getName());
                Assertions.assertEquals(bean, callData.getBean());
            }

        }
    }

    @GetMapping("/asyncTest2")
    public void asyncTest2() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            asyncService.getCallDatas().clear();

            Long id = RandomUtils.nextLong();
            String name = String.valueOf(RandomUtils.nextLong());
            AsyncInputBean bean = createAsyncInputBean();
            asyncService.asyncTest2(id, name, bean);

            {
                List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
                Assertions.assertTrue(CollectionUtils.isEmpty(callDatas));
            }

            TimeUnit.SECONDS.sleep(2);

            {
                List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
                Assertions.assertFalse(CollectionUtils.isEmpty(callDatas));

                AsyncService.CallData callData = callDatas.get(0);
                Assertions.assertEquals(id, callData.getId());
                Assertions.assertEquals(name, callData.getName());
                Assertions.assertEquals(bean, callData.getBean());
            }

        }
    }

    @GetMapping("/asyncForOrderTest1")
    public void asyncForOrderTest1() throws InterruptedException {

        List<InputData> inputDatas = new ArrayList<>();
        Long[] ids = new Long[]{RandomUtils.nextLong(), RandomUtils.nextLong(),
                RandomUtils.nextLong(), RandomUtils.nextLong()};
        String name = String.valueOf(RandomUtils.nextLong());
        AsyncInputBean bean = createAsyncInputBean();

        asyncService.getCallDatas().clear();
        asyncService.asyncTestForOrder1(ids[0], name, bean);
        inputDatas.add(new InputData(ids[0], name, bean));

        {
            List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
            Assertions.assertTrue(CollectionUtils.isEmpty(callDatas));
        }


        for (int i = 0; i < 100; i++) {
            name = String.valueOf(RandomUtils.nextLong());
            bean = createAsyncInputBean();
            asyncService.asyncTestForOrder1(ids[i % ids.length], name, bean);
            inputDatas.add(new InputData(ids[i % ids.length], name, bean));
        }


        TimeUnit.SECONDS.sleep(10);

        {
            List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
            Assertions.assertFalse(CollectionUtils.isEmpty(callDatas));

            Assertions.assertEquals(inputDatas.size(), callDatas.size());

            Map<Long, List<AsyncService.CallData>> callDataMap = callDatas.stream()
                    .collect(Collectors.groupingBy(AsyncService.CallData::getId));
            Map<Long, List<InputData>> inputDataMap = inputDatas.stream()
                    .collect(Collectors.groupingBy(InputData::getId));

            for (Long id : ids) {
                List<AsyncService.CallData> callDataToCheck = callDataMap.get(id);
                List<InputData> inputDataToCheck = inputDataMap.get(id);

                Assertions.assertEquals(callDataToCheck.size(), inputDataToCheck.size());

                for (int j = 0; j < callDataToCheck.size(); j++) {
                    AsyncService.CallData callData = callDataToCheck.get(j);
                    InputData inputData1 = inputDataToCheck.get(j);

                    Assertions.assertEquals(inputData1.getId(), callData.getId());
                    Assertions.assertEquals(inputData1.getName(), callData.getName());
                    Assertions.assertEquals(inputData1.getBean(), callData.getBean());
                }
            }
        }

    }

    @GetMapping("/asyncForOrderTest2")
    public void asyncForOrderTest2() throws InterruptedException {

        List<InputData> inputDatas = new ArrayList<>();
        Long[] ids = new Long[]{RandomUtils.nextLong(), RandomUtils.nextLong(),
                RandomUtils.nextLong(), RandomUtils.nextLong()};
        String name = String.valueOf(RandomUtils.nextLong());
        AsyncInputBean bean = createAsyncInputBean();

        asyncService.getCallDatas().clear();
        asyncService.asyncTestForOrder2(ids[0], name, bean);
        inputDatas.add(new InputData(ids[0], name, bean));

        {
            List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
            Assertions.assertTrue(CollectionUtils.isEmpty(callDatas));
        }


        for (int i = 0; i < 100; i++) {
            name = String.valueOf(RandomUtils.nextLong());
            bean = createAsyncInputBean();
            asyncService.asyncTestForOrder2(ids[i % ids.length], name, bean);
            inputDatas.add(new InputData(ids[i % ids.length], name, bean));
        }


        TimeUnit.SECONDS.sleep(10);

        {
            List<AsyncService.CallData> callDatas = this.asyncService.getCallDatas();
            Assertions.assertFalse(CollectionUtils.isEmpty(callDatas));

            Assertions.assertEquals(inputDatas.size(), callDatas.size());

            Map<Long, List<AsyncService.CallData>> callDataMap = callDatas.stream()
                    .collect(Collectors.groupingBy(AsyncService.CallData::getId));
            Map<Long, List<InputData>> inputDataMap = inputDatas.stream().collect(Collectors.groupingBy(InputData::getId));

            for (Long id : ids) {
                List<AsyncService.CallData> callDataToCheck = callDataMap.get(id);
                List<InputData> inputDataToCheck = inputDataMap.get(id);

                Assertions.assertEquals(callDataToCheck.size(), inputDataToCheck.size());

                for (int j = 0; j < callDataToCheck.size(); j++) {
                    AsyncService.CallData callData = callDataToCheck.get(j);
                    InputData inputData1 = inputDataToCheck.get(j);

                    Assertions.assertEquals(inputData1.getId(), callData.getId());
                    Assertions.assertEquals(inputData1.getName(), callData.getName());
                    Assertions.assertEquals(inputData1.getBean(), callData.getBean());
                }
            }
        }

    }

    private AsyncInputBean createAsyncInputBean() {
        AsyncInputBean bean = new AsyncInputBean();
        bean.setAge(RandomUtils.nextInt());
        bean.setName(String.valueOf(RandomUtils.nextInt()));
        bean.setId(RandomUtils.nextLong());
        return bean;
    }

    @Data
    static class InputData {
        private final Long id;
        private final String name;
        private final AsyncInputBean bean;
    }

}
