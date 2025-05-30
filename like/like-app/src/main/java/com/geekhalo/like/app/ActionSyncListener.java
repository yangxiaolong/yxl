package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeCancelledEvent;
import com.geekhalo.like.domain.dislike.DislikeMarkedEvent;
import com.geekhalo.like.domain.like.LikeCancelledEvent;
import com.geekhalo.like.domain.like.LikeMarkedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ActionSyncListener {
    @Autowired
    private TargetCountCommandApplicationService targetCountCommandApplicationService;

    @EventListener
    public void handle(LikeMarkedEvent event){
        this.targetCountCommandApplicationService.incrLike(event.source().getTarget(), 1);
    }

    @EventListener
    public void handle(LikeCancelledEvent event){
        this.targetCountCommandApplicationService.incrLike(event.source().getTarget(), -1);
    }

    @EventListener
    public void handle(DislikeMarkedEvent event){
        this.targetCountCommandApplicationService.incrDislike(event.source().getTarget(), 1);
    }

    @EventListener
    public void handle(DislikeCancelledEvent event){
        this.targetCountCommandApplicationService.incrDislike(event.source().getTarget(), -1);
    }
}
