package com.geekhalo.like.domain.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekhalo.like.domain.AbstractAction;
import com.geekhalo.like.domain.ActionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "like_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class LikeAction extends AbstractAction {

    public static LikeAction create(LikeActionContext context) {
        LikeAction likeAction = new LikeAction();
        likeAction.init(context);
        return likeAction;
    }

    public void like(LikeActionContext context) {
        if (!checkedValid()) {
            changeToStatus = ActionStatus.VALID;
            LikeAction clone = changeStatus();
            addEvent(LikeMarkedEvent.apply(clone));
        }
    }

    @SneakyThrows
    private LikeAction changeStatus() {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(this);
        LikeAction clone = objectMapper.readValue(s, LikeAction.class);
        clone.setStatus(changeToStatus);
        return clone;
    }

    public void unlike(UnlikeActionContext context) {
        if (checkedValid()) {
            changeToStatus = ActionStatus.INVALID;
            LikeAction clone = changeStatus();
            addEvent(LikeCancelledEvent.apply(clone));
        }
    }
}
