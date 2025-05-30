package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TargetCountQueryApplicationService {
    @Autowired
    private LikeTargetCountRepository likeTargetCountRepository;
    @Autowired
    private DislikeTargetCountRepository dislikeTargetCountRepository;

    public List<LikeTargetCount> getLikeCountByTarget(String type, List<Long> ids) {
        return this.likeTargetCountRepository.getByTargetTypeAndTargetIdIn(type, ids);
    }

    public List<DislikeTargetCount> getDislikeCountByType(String type, List<Long> ids) {
        return this.dislikeTargetCountRepository.getByTargetTypeAndTargetIdIn(type, ids);
    }

}
