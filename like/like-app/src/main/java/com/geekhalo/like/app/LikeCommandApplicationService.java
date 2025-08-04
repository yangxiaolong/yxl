package com.geekhalo.like.app;

import com.geekhalo.like.domain.like.LikeActionCommand;
import com.geekhalo.like.domain.like.UnlikeActionCommand;
import com.lego.yxl.core.command.NoCommandApplicationService;

@NoCommandApplicationService
public interface LikeCommandApplicationService {
    void like(LikeActionCommand command);

    void unLike(UnlikeActionCommand command);
}
