package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeActionCommand;
import com.geekhalo.like.domain.dislike.UndislikeActionCommand;
import com.lego.yxl.core.command.NoCommandApplicationService;

@NoCommandApplicationService
public interface DislikeCommandApplicationService {
    void dislike(DislikeActionCommand command);

    void unDislike(UndislikeActionCommand command);
}
