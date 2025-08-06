package com.geekhalo.like.domain.dislike;

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
@Table(name = "dislike_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class DislikeAction extends AbstractAction {

    public static DislikeAction create(DislikeActionContext context) {
        DislikeAction dislikeAction = new DislikeAction();
        dislikeAction.init(context);
        return dislikeAction;
    }

    @SneakyThrows
    public void like(DislikeActionContext context) {
        if (!checkedValid()) {
            changeToStatus = ActionStatus.VALID;
            DislikeAction clone = changeStatus();
            addEvent(DislikeMarkedEvent.apply(clone));
        }
    }

    @SneakyThrows
    private DislikeAction changeStatus() {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(this);
        DislikeAction clone = objectMapper.readValue(s, DislikeAction.class);
        clone.setStatus(changeToStatus);
        return clone;
    }

    @SneakyThrows
    public void unlike(UndislikeActionContext context) {
        if (checkedValid()) {
            changeToStatus = ActionStatus.INVALID;
            DislikeAction clone = changeStatus();
            addEvent(DislikeCancelledEvent.apply(clone));
        }
    }
}
