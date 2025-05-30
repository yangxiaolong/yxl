package com.geekhalo.like.app;

import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.user.ActionUser;

import java.util.List;

//@NoQueryApplicationService
public interface LikeQueryApplicationService {
    List<LikeAction> getLikeByUserAndType(ActionUser user, String type);
}
