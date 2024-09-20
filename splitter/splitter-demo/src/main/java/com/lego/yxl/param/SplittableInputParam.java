package com.lego.yxl.param;

import com.google.common.collect.Lists;
import com.lego.yxl.common.SplittableParam;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
public class SplittableInputParam implements SplittableParam<SplittableInputParam> {

    List<Long> numbers;
    Long other;

    @Override
    public List<SplittableInputParam> split(int maxSize) {
        List<List<Long>> partition = Lists.partition(this.numbers, maxSize);
        return partition.stream()
                .map(ns -> SplittableInputParam.builder()
                        .numbers(ns)
                        .other(other)
                        .build()
                ).collect(toList());
    }

}