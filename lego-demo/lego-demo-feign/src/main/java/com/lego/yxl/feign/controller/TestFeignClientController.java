package com.lego.yxl.feign.controller;

import com.lego.yxl.core.feign.RpcException;
import com.lego.yxl.feign.feign.CustomException;
import com.lego.yxl.feign.feign.TestFeignClient;
import com.lego.yxl.feign.feign.TestFeignService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/feign")
public class TestFeignClientController {

    @Autowired
    private TestFeignClient testFeignClient;
    @Autowired
    private TestFeignService testFeignService;

    private String key;
    private List<Long> data;

    void setUp() {
        this.key = String.valueOf(RandomUtils.nextLong());
        this.data = Arrays.asList(RandomUtils.nextLong(), RandomUtils.nextLong(),
                RandomUtils.nextLong(), RandomUtils.nextLong());
    }

    @RequestMapping("/postData")
    void postData() {
        setUp();

        this.testFeignClient.postData(key, data);
        Assertions.assertEquals(data, this.testFeignService.getData(key));
    }

    @RequestMapping("/postDataForError")
    void postDataForError() {
        setUp();

        Assertions.assertThrows(RpcException.class, () -> {
            this.testFeignClient.postDataForError(key, data);
        });
    }

    @RequestMapping("/getData")
    void getData() {
        setUp();

        this.testFeignClient.postData(key, data);

        List<Long> data = this.testFeignService.getData(key);
        Assertions.assertEquals(data, this.data);

        List<Long> ds = this.testFeignClient.getData(key);
        Assertions.assertEquals(ds, this.data);
    }

    @RequestMapping("/getDataForError")
    void getDataForError() {
        setUp();

        this.testFeignClient.getData(key);
        Assertions.assertThrows(RpcException.class, () -> {
            this.testFeignClient.getDataForError(key);
        });
    }

    @RequestMapping("/customException")
    void customException() {
        setUp();

        Assertions.assertThrows(CustomException.class, () -> {
            this.testFeignClient.customException();
        });
    }

}
