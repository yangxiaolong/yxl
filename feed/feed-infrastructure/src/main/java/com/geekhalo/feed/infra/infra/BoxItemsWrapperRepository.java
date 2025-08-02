package com.geekhalo.feed.infra.infra;

import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.geekhalo.feed.domain.feed.OwnerType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoxItemsWrapperRepository extends JpaRepository<BoxItemsWrapper, Long> {

    default Optional<BoxItemsWrapper> getFirstMatch(
            FeedOwner feedOwner,
            BoxType boxType,
            Long score){
        Pageable pageable = PageRequest.of(0, 1);
        List<BoxItemsWrapper> boxItemsWrapperList = getFirstMatch(feedOwner.getOwnerId(), feedOwner.getOwnerType(), boxType, score, pageable);
        return boxItemsWrapperList.stream()
                .findFirst();
    }

    @Query(
            value = "SELECT b " +
                    "FROM BoxItemsWrapper b " +
                    "WHERE b.feedOwner.ownerId = :ownerId " +
                    "AND b.feedOwner.ownerType = :ownerType " +
                    "AND b.type = :type " +
                    "AND b.minScore < :score " +
                    "ORDER BY b.minScore desc " +
                    ""
    )

    List<BoxItemsWrapper> getFirstMatch(
            @Param("ownerId") Long ownerId,
            @Param("ownerType") OwnerType ownerType,
            @Param("type") BoxType boxType,
            @Param("score") Long score,
            Pageable pageable);
}
