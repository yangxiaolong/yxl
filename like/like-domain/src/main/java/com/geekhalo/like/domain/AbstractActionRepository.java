package com.geekhalo.like.domain;

import com.geekhalo.like.domain.target.ActionTarget;
import com.geekhalo.like.domain.user.ActionUser;
import com.lego.yxl.core.command.CommandWithKeyRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AbstractActionRepository<A extends AbstractAction>
        extends CommandWithKeyRepository<A, Long, ActionKey>
        /*,QueryRepository<A, Long>*/ {
    Optional<A> getByUserAndTarget(ActionUser user, ActionTarget target);

    List<A> getByUserAndTargetType(ActionUser user, String type);

    @Override
    default Optional<A> findByKey(ActionKey key) {
        return getByUserAndTarget(key.getActionUser(), key.getActionTarget());
    }

    @Modifying
    @Query("UPDATE #{#entityName} a " +
            "set " +
            "   a.status = :status," +
            "   a.updateAt =:updateDate, " +
            "   a.vsn = :vsn + 1 " +
            "where " +
            "   a.id = :id and a.vsn = :vsn and a.user = :user")
    void updateByIdAndUserId(@Param("status") ActionStatus status,
                             @Param("updateDate") Date updateDate,
                             @Param("id") Long id,
                             @Param("vsn") Integer vsn,
                             @Param("user") ActionUser user);
}
