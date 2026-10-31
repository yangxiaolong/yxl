package com.lego.yxl.splitter.controller;

import com.lego.yxl.splitter.param.AnnBasedInputParam;
import com.lego.yxl.splitter.param.SplittableInputParam;
import com.lego.yxl.splitter.param.UserPO;
import com.lego.yxl.splitter.service.SplitTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/splitter")
@Slf4j
public class SplitterController {

    private static final List<Long> params = new ArrayList<>();

    static {
        for (long i = 1L; i <= 100L; i++) {
            params.add(i);
        }
    }

    @Autowired
    private SplitTestService splitTestService;

    @GetMapping("/splitByList")
    public void splitByList() {
        List<UserPO> longs = this.splitTestService.splitByList(params);
        Assertions.assertEquals(100, longs.size());
    }

    @GetMapping("/testSplitByList")
    public void testSplitByList() {
        List<UserPO> longs = this.splitTestService.splitByList(params, 10L);
        Assertions.assertEquals(100, longs.size());
    }

    @GetMapping("/splitByListAsSet")
    public void splitByListAsSet() {
        Set<UserPO> longs = this.splitTestService.splitByListAsSet(params);
        Assertions.assertEquals(100, longs.size());
    }

    @GetMapping("/testSplitByListAsSet")
    public void testSplitByListAsSet() {
        Set<UserPO> longs = this.splitTestService.splitByListAsSet(params, 10L);
        Assertions.assertEquals(100, longs.size());
    }

    @GetMapping("/splitByListAsCount")
    public void splitByListAsCount() {
        Integer count = this.splitTestService.splitByListAsCount(params);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/testSplitByListAsCount")
    public void testSplitByListAsCount() {
        Integer count = this.splitTestService.splitByListAsCount(params, 10L);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/splitByListAsLong")
    public void splitByListAsLong() {
        Long count = this.splitTestService.splitByListAsLong(params);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/testSplitByListAsLong")
    public void testSplitByListAsLong() {
        Long count = this.splitTestService.splitByListAsLong(params, 10L);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/splitByListAsLong2")
    public void splitByListAsLong2() {
        long count = this.splitTestService.splitByListAsLong2(params);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/testSplitByListAsLong2")
    public void testSplitByListAsLong2() {
        long count = this.splitTestService.splitByListAsLong2(params, 10L);
        Assertions.assertEquals(100, count);
    }

    @GetMapping("/splitByParam")
    public void splitByParam() {
        AnnBasedInputParam annBasedInputParam = AnnBasedInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<UserPO> longs = this.splitTestService.splitByParam(annBasedInputParam);
        Assertions.assertEquals(100, longs.size());
    }

    @GetMapping("/splitByParam_Splittable")
    public void splitByParam_Splittable() {
        SplittableInputParam splittableInputParam = SplittableInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<UserPO> longs = this.splitTestService.splitByParam(splittableInputParam);
        Assertions.assertEquals(100, longs.size());
    }

}
