package com.lego.yxl.splitter.core.support.merger;

import com.lego.yxl.splitter.core.SmartResultMerger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class SetResultMerger extends AbstractFixTypeResultMerger<Set> implements SmartResultMerger<Set> {
    @Override
    Set doMerge(List<Set> sets) {
        return (Set) sets.stream()
                .flatMap(s -> s.stream())
                .collect(Collectors.toSet());
    }
}
