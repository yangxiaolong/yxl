package com.geekhalo.like.infra.like;

import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBasedLikeActionRepository
        extends LikeActionRepository, JpaRepository<LikeAction, Long> {

    @Override
    default LikeAction sync(LikeAction likeAction) {
        return save(likeAction);
//        if (likeAction.getId() == null && likeAction.getVsn() == null) {
//            return save(likeAction);
//        } else {
//            updateByIdAndUserId(likeAction.getStatus(),
//                    likeAction.getUpdateAt(),
//                    likeAction.getId(),
//                    likeAction.getVsn(),
//                    likeAction.getUser());
//            return likeAction;
//        }
    }

}
