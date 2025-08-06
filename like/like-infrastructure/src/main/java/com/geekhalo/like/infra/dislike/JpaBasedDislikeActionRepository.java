package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.ActionStatus;
import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Objects;

public interface JpaBasedDislikeActionRepository
        extends DislikeActionRepository, JpaRepository<DislikeAction, Long> {

    @Override
    default DislikeAction sync(DislikeAction dislikeAction) {
//        return this.save(dislikeAction);
        ActionStatus changeToStatus = dislikeAction.getChangeToStatus();
        if (Objects.isNull(changeToStatus)) {
            return dislikeAction;// 状态不需要改变, 直接返回
        }

        if (dislikeAction.getId() == null && dislikeAction.getVsn() == null) {
            return save(dislikeAction);
        } else {
            updateByIdAndUserId(changeToStatus,
                    new Date(),
                    dislikeAction.getId(),
                    dislikeAction.getVsn(),
                    dislikeAction.getUser());
            return dislikeAction;
        }
    }

}
