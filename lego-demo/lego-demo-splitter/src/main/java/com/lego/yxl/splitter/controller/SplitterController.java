package com.lego.yxl.splitter.controller;

import com.google.common.collect.Lists;
import com.lego.yxl.splitter.param.AnnBasedInputParam;
import com.lego.yxl.splitter.param.SplittableInputParam;
import com.lego.yxl.splitter.service.SplitTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/splitter")
@Slf4j
public class SplitterController {

    @Autowired
    private SplitTestService splitTestService;

    @GetMapping("/splitByList")
    public void splitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params);
        Assertions.assertEquals(8, longs.size());
    }

    @GetMapping("/testSplitByList")
    public void testSplitByList() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        List<Long> longs = this.splitTestService.splitByList(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @GetMapping("/splitByListAsSet")
    public void splitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params);
        Assertions.assertEquals(8, longs.size());
    }

    @GetMapping("/testSplitByListAsSet")
    public void testSplitByListAsSet() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Set<Long> longs = this.splitTestService.splitByListAsSet(params, 10L);
        Assertions.assertEquals(8, longs.size());
    }

    @GetMapping("/splitByListAsCount")
    public void splitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/testSplitByListAsCount")
    public void testSplitByListAsCount() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Integer count = this.splitTestService.splitByListAsCount(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/splitByListAsLong")
    public void splitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/testSplitByListAsLong")
    public void testSplitByListAsLong() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        Long count = this.splitTestService.splitByListAsLong(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/splitByListAsLong2")
    public void splitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/testSplitByListAsLong2")
    public void testSplitByListAsLong2() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        long count = this.splitTestService.splitByListAsLong2(params, 10L);
        Assertions.assertEquals(8, count);
    }

    @GetMapping("/splitByParam")
    public void splitByParam() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        AnnBasedInputParam annBasedInputParam = AnnBasedInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<Long> longs = this.splitTestService.splitByParam(annBasedInputParam);
        Assertions.assertEquals(8, longs.size());
    }

    @GetMapping("/splitByParam_Splittable")
    public void splitByParam_Splittable() {
        List<Long> params = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        SplittableInputParam splittableInputParam = SplittableInputParam.builder()
                .numbers(params)
                .other(10L)
                .build();
        List<Long> longs = this.splitTestService.splitByParam(splittableInputParam);
        Assertions.assertEquals(8, longs.size());
    }

}
