package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.*;
import com.geekhalo.like.domain.like.*;
import com.lego.yxl.command.core.support.AbstractCommandService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
@Transactional
class ActionCommandApplicationServiceImpl extends AbstractCommandService
        implements LikeCommandApplicationService,
        DislikeCommandApplicationService {

    @Autowired
    private LikeActionRepository likeActionRepository;

    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    @Override
    public void like(LikeActionCommand command) {
        this.<LikeAction, LikeActionCommand, LikeActionContext>syncerFor(this.likeActionRepository)
                .loadBy(cmd -> this.likeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(LikeActionContext::apply)
                .instanceBy(LikeAction::create)
                .update((LikeAction::like))
                .exe(command);
    }


    @Override
    public void unLike(UnlikeActionCommand command) {

        this.<LikeAction, UnlikeActionCommand, UnlikeActionContext>updaterFor(this.likeActionRepository)
                .loadBy(cmd -> this.likeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(UnlikeActionContext::apply)
                .update(LikeAction::unlike)
                .exe(command);
    }


    @Override
    public void dislike(DislikeActionCommand command) {

        this.<DislikeAction, DislikeActionCommand, DislikeActionContext>syncerFor(this.dislikeActionRepository)
                .loadBy(cmd -> this.dislikeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(DislikeActionContext::apply)
                .instanceBy(DislikeAction::create)
                .update(DislikeAction::like)
                .exe(command);
    }

    @Override
    public void unDislike(UndislikeActionCommand command) {

        this.<DislikeAction, UndislikeActionCommand, UndislikeActionContext>updaterFor(this.dislikeActionRepository)
                .loadBy(cmd -> this.dislikeActionRepository.findByKey(cmd.getKey()))
                .contextFactory(UndislikeActionContext::apply)
                .update(DislikeAction::unlike)
                .exe(command);
    }
}
