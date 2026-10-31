package com.lego.yxl.splitter.service;

import com.google.common.base.Preconditions;
import com.lego.yxl.core.splitter.annotation.Split;
import com.lego.yxl.core.splitter.annotation.SplitParam;
import com.lego.yxl.splitter.param.AnnBasedInputParam;
import com.lego.yxl.splitter.param.SplittableInputParam;
import com.lego.yxl.splitter.param.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SplitTestService {

    @Split(sizePrePartition = 40, executor = "defaultSplitExecutor1")
    public List<UserPO> splitByList(List<Long> params) {
        return convert(params, null);
    }

    @Split(sizePrePartition = 40)
    public List<UserPO> splitByList(@SplitParam List<Long> params, Long other) {
        Preconditions.checkArgument(other != null);
        return convert(params, other);
    }

    @Split(sizePrePartition = 40)
    public Set<UserPO> splitByListAsSet(List<Long> params) {
        return new HashSet<>(convert(params, null));
    }

    @Split(sizePrePartition = 40)
    public Set<UserPO> splitByListAsSet(@SplitParam List<Long> params, Long other) {
        Preconditions.checkArgument(other != null);
        return new HashSet<>(convert(params, other));
    }

    @Split(sizePrePartition = 40)
    public Integer splitByListAsCount(List<Long> params) {
        return convert(params, null).size();
    }

    @Split(sizePrePartition = 40)
    public Integer splitByListAsCount(@SplitParam List<Long> params, Long other) {
        Preconditions.checkArgument(other != null);
        return convert(params, other).size();
    }

//    @Split(sizePrePartition = 40)
//    public int splitByListAsCount2(List<Long> params) {
//        return convert(params, null).size();
//    }
//
//    @Split(sizePrePartition = 40)
//    public int splitByListAsCount2(@SplitParam List<Long> params, Long other) {
//        Preconditions.checkArgument(other != null);
//        return convert(params, other).size();
//    }

    @Split(sizePrePartition = 40)
    public long splitByListAsLong(List<Long> params) {
        return convert(params, null).size();
    }

    @Split(sizePrePartition = 40)
    public long splitByListAsLong(@SplitParam List<Long> params, Long other) {
        Preconditions.checkArgument(other != null);
        return convert(params, other).size();
    }

    @Split(sizePrePartition = 40)
    public long splitByListAsLong2(List<Long> params) {
        return convert(params, null).size();
    }

    @Split(sizePrePartition = 40)
    public long splitByListAsLong2(@SplitParam List<Long> params, Long other) {
        Preconditions.checkArgument(other != null);
        return convert(params, other).size();
    }

    @Split(sizePrePartition = 40)
    public List<UserPO> splitByParam(AnnBasedInputParam param) {
        Preconditions.checkArgument(param.getOther() != null);
        return convert(param.getNumbers(), param.getOther());
    }

    @Split(sizePrePartition = 40)
    public List<UserPO> splitByParam(SplittableInputParam param) {
        Preconditions.checkArgument(param.getOther() != null);
        return convert(param.getNumbers(), param.getOther());
    }


    private List<UserPO> convert(List<Long> input, Long other) {
        if (input.size() > 1000) {
            throw new RuntimeException("Many Param");
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("failed to sleep", e);
            Thread.currentThread().interrupt();
        }
        log.info("Thread {} run with {}", Thread.currentThread().getName(), input);
        return input.stream().map(s -> new UserPO(s, other)).collect(Collectors.toList());
    }
}
