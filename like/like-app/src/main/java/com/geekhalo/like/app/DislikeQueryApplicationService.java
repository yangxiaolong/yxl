package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

//@NoQueryApplicationService
public interface DislikeQueryApplicationService {
    List<DislikeAction> getDislikeByUserAndType(ActionUser user, String type);
}
