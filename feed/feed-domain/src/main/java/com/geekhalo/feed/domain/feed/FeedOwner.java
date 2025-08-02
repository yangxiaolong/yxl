package com.geekhalo.feed.domain.feed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FeedOwner {
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private OwnerType ownerType;

    @Column(updatable = false)
    private Long ownerId;
}
