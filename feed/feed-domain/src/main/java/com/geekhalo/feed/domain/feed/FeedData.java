package com.geekhalo.feed.domain.feed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class FeedData {
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private FeedDataType type;

    @Column(updatable = false)
    private String content;
}
