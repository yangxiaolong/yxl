package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.google.common.base.Preconditions;
import com.lego.yxl.core.command.support.AbstractAggRoot;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractAction extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ActionUser user;

    @Embedded
    private ActionTarget target;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActionStatus status;

    @Transient
    protected ActionStatus changeToStatus;

    protected void init(AbstractActionContext context) {
        Preconditions.checkArgument(context != null);
        setUser(context.getActionUser());
        setTarget(context.getActionTarget());
    }

    protected boolean cancel() {
        if (getStatus() != ActionStatus.INVALID) {
            setStatus(ActionStatus.INVALID);
            return true;
        }
        return false;
    }

    protected boolean mark() {
        if (getStatus() != ActionStatus.VALID) {
            setStatus(ActionStatus.VALID);
            return true;
        }
        return false;
    }

    public boolean checkedValid() {
        return this.getStatus() == ActionStatus.VALID;
    }
}
