package com.geekhalo.like.domain.user;

import com.google.common.base.Preconditions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionUser {
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Transient
    private boolean valid;

    public boolean isValid(){
        return this.valid;
    }
    public static ActionUser apply(Long userId){
        return apply(userId, true);
    }

    public static ActionUser apply(Long userId, boolean valid){
        Preconditions.checkArgument(userId != null);
        ActionUser actionUser = new ActionUser();
        actionUser.setUserId(userId);
        actionUser.setValid(valid);
        return actionUser;
    }
}
