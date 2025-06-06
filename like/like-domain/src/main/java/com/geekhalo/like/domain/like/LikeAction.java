package com.geekhalo.like.domain.like;

import com.geekhalo.like.domain.AbstractAction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "like_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LikeAction extends AbstractAction {

    public static LikeAction create(LikeActionContext context) {
        LikeAction likeAction = new LikeAction();
        likeAction.init(context);
        return likeAction;
    }

    public void like(LikeActionContext context) {
        if (mark()) {
            addEvent(LikeMarkedEvent.apply(this));
        }
    }

    public void unlike(UnlikeActionContext context) {
        if (cancel()) {
            addEvent(LikeCancelledEvent.apply(this));
        }
    }
}
