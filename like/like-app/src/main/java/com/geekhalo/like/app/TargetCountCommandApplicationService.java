package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeTargetCount;
import com.geekhalo.like.domain.dislike.DislikeTargetCountRepository;
import com.geekhalo.like.domain.like.LikeTargetCount;
import com.geekhalo.like.domain.like.LikeTargetCountRepository;
import com.geekhalo.like.domain.target.ActionTarget;
import com.lego.yxl.core.command.support.AbstractCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class TargetCountCommandApplicationService extends AbstractCommandService {
    @Autowired
    private LikeTargetCountRepository likeTargetCountRepository;

    @Autowired
    private DislikeTargetCountRepository dislikeTargetCountRepository;

    public void incrLike(ActionTarget target, int count) {
        Optional<LikeTargetCount> targetCountOptional = this.likeTargetCountRepository.getByTarget(target);
        if (!targetCountOptional.isPresent()) {
            LikeTargetCount likeTargetCount = LikeTargetCount.create(target);
            this.likeTargetCountRepository.sync(likeTargetCount);
        }
        this.likeTargetCountRepository.incr(target, count);
    }

    public void incrDislike(ActionTarget target, int count) {
        Optional<DislikeTargetCount> targetCountOptional = this.dislikeTargetCountRepository.getByTarget(target);
        if (!targetCountOptional.isPresent()) {
            DislikeTargetCount targetCount = DislikeTargetCount.create(target);
            this.dislikeTargetCountRepository.sync(targetCount);
        }
        this.dislikeTargetCountRepository.incr(target, count);
    }
}
