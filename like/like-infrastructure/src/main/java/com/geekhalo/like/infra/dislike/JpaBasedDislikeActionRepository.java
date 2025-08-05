package com.geekhalo.like.infra.dislike;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedDislikeActionRepository
        extends DislikeActionRepository, JpaRepository<DislikeAction, Long> {

    @Override
    default DislikeAction sync(DislikeAction dislikeAction) {
        return this.save(dislikeAction);
//        if (dislikeAction.getId() == null && dislikeAction.getVsn() == null) {
//            return save(dislikeAction);
//        } else {
//            updateByIdAndUserId(dislikeAction.getStatus(),
//                    dislikeAction.getUpdateAt(),
//                    dislikeAction.getId(),
//                    dislikeAction.getVsn(),
//                    dislikeAction.getUser());
//            return dislikeAction;
//        }
    }

}
