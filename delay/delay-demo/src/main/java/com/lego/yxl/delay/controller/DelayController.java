package com.lego.yxl.delay.controller;

import com.lego.yxl.delay.service.DelayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @auther yangxiaolong
 * @create 2024/9/26
 */
@RestController
@RequestMapping(value = "/delay")
@Slf4j
public class DelayController {

    @Autowired
    DelayService delayService;

    @GetMapping("/delayCancelOrder")
    public void delayCancelOrder() throws Exception {
        this.delayService.clean();

        Long orderId = RandomUtils.nextLong();
        String reason = "超时自动取消";

        this.delayService.delayCancelOrder(orderId, reason);

        Assertions.assertTrue(CollectionUtils.isEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(4);

        Assertions.assertTrue(CollectionUtils.isEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(6);

        Assertions.assertFalse(CollectionUtils.isEmpty(this.delayService.getTasks()));

    }

    @GetMapping("/delayCancelOrder_DelayTime")
    public void delayCancelOrder_DelayTime() throws Exception {
        this.delayService.clean();

        Long orderId = RandomUtils.nextLong();
        String reason = "超时自动取消";


        this.delayService.delayCancelOrderForTimeout(orderId, reason, 3);

        Assertions.assertTrue(CollectionUtils.isEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(9);

        Assertions.assertTrue(CollectionUtils.isEmpty(this.delayService.getTasks()));

        TimeUnit.SECONDS.sleep(11);

        Assertions.assertFalse(CollectionUtils.isEmpty(this.delayService.getTasks()));

    }

}
