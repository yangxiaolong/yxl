package com.geekhalo.like.feign.service;

import com.geekhalo.like.api.TargetCountQueryApi;
import com.geekhalo.like.api.TargetCountVO;
import com.geekhalo.like.app.TargetCountQueryApplicationService;
import com.geekhalo.like.domain.AbstractTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(TargetCountQueryApi.PATH)
public class TargetCountQueryFeignService implements TargetCountQueryApi {
    @Autowired
    private TargetCountQueryApplicationService targetCountQueryApplicationService;

    @Override
    public List<TargetCountVO> getLikeCountByTarget(String type, List<Long> ids) {
        List<LikeTargetCount> targetCounts = this.targetCountQueryApplicationService.getLikeCountByTarget(type, ids);
        return targetCounts.stream()
                .map(this::toTargetCountVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TargetCountVO> getDislikeCountByType(String type, List<Long> ids) {
        List<DislikeTargetCount> targetCounts = this.targetCountQueryApplicationService.getDislikeCountByType(type, ids);
        return targetCounts.stream()
                .map(this::toTargetCountVO)
                .collect(Collectors.toList());
    }

    private TargetCountVO toTargetCountVO(AbstractTargetCount targetCount) {
        if (targetCount == null) {
            return null;
        }
        TargetCountVO targetCountVO = new TargetCountVO();
        targetCountVO.setTargetType(targetCount.getTarget().getType());
        targetCountVO.setTargetId(targetCount.getTarget().getId());
        targetCountVO.setCount(targetCount.getCount());
        return targetCountVO;
    }
}
