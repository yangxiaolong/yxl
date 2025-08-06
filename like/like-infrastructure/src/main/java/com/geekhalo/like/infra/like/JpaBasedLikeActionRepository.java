package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.ActionStatus;
import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Objects;

public interface JpaBasedLikeActionRepository
        extends LikeActionRepository, JpaRepository<LikeAction, Long> {

    @Override
    default LikeAction sync(LikeAction likeAction) {
//        return save(likeAction);
        ActionStatus changeToStatus = likeAction.getChangeToStatus();
        if (Objects.isNull(changeToStatus)) {
            return likeAction;// 状态不需要改变, 直接返回
        }

        if (likeAction.getId() == null && likeAction.getVsn() == null) {
            return save(likeAction);
        } else {
            updateByIdAndUserId(changeToStatus,
                    new Date(),
                    likeAction.getId(),
                    likeAction.getVsn(),
                    likeAction.getUser());
            return likeAction;
        }
    }

}
