package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.geekhalo.like.domain.user.ActionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionQueryApplicationService implements LikeQueryApplicationService, DislikeQueryApplicationService {
    @Autowired
    private LikeActionRepository likeActionRepository;
    @Autowired
    private DislikeActionRepository dislikeActionRepository;

    @Override
    public List<LikeAction> getLikeByUserAndType(ActionUser user, String type) {
//         = ActionUser.apply(userId);
        return this.likeActionRepository.getByUserAndTargetType(user, type);
    }

    @Override
    public List<DislikeAction> getDislikeByUserAndType(ActionUser user, String type) {
//        ActionUser user = ActionUser.apply(userId);
        return this.dislikeActionRepository.getByUserAndTargetType(user, type);
    }

}
