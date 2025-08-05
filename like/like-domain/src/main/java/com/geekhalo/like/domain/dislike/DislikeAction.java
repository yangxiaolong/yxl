package com.geekhalo.like.domain.dislike;

import com.geekhalo.like.domain.AbstractAction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "dislike_action")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class DislikeAction extends AbstractAction {

    public static DislikeAction create(DislikeActionContext context){
        DislikeAction dislikeAction = new DislikeAction();
        dislikeAction.init(context);
        return dislikeAction;
    }

    public void like(DislikeActionContext context){
        if (mark()){
            addEvent(DislikeMarkedEvent.apply(this));
        }
    }

    public void unlike(UndislikeActionContext context){
        if (cancel()){
            addEvent(DislikeCancelledEvent.apply(this));
        }
    }
}
