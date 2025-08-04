package com.geekhalo.feed.infra.infra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.lego.yxl.core.command.support.AbstractAggRoot;
import com.lego.yxl.core.util.SerializeUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Table(name = "feed_box")
@Entity
@Setter(AccessLevel.PRIVATE)
public class BoxItemsWrapper extends AbstractAggRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private FeedOwner feedOwner;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private BoxType type;

    private Long minScore;

    private Long maxScore;

    private Integer itemCount = 0;

    private String data;

    public static BoxItemsWrapper apply(FeedOwner feedOwner,
                                        BoxType type) {
        BoxItemsWrapper boxItemsWrapper = new BoxItemsWrapper();
        boxItemsWrapper.setFeedOwner(feedOwner);
        boxItemsWrapper.setType(type);
        return boxItemsWrapper;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public boolean canAppend(int maxSize, Long score) {
        return !isFull(maxSize) || score < getMaxScore();
    }

    public boolean isFull(int maxPreRow) {
        return this.itemCount >= maxPreRow;
    }

    public void append(FeedIndex feedIndex) {
        List<FeedIndex> feedIndicesList = parseFeedIndex();
        feedIndicesList.add(feedIndex);
        update(feedIndicesList);
    }

    private void update(List<FeedIndex> feedIndicesList) {
        updateMaxScore(feedIndicesList);
        updateMinScore(feedIndicesList);
        updateSize(feedIndicesList);
        updateData(feedIndicesList);
    }

    private void updateData(List<FeedIndex> feedIndicesList) {
        String data = SerializeUtil.serialize(feedIndicesList);
        setData(data);
    }

    private void updateSize(List<FeedIndex> feedIndicesList) {
        setItemCount(feedIndicesList.size());
    }

    private void updateMinScore(List<FeedIndex> feedIndicesList) {
        Long minScore = feedIndicesList.stream()
                .mapToLong(FeedIndex::getScore)
                .min()
                .orElse(Integer.MIN_VALUE);
        setMinScore(minScore);
    }

    private void updateMaxScore(List<FeedIndex> feedIndicesList) {
        Long maxScore = feedIndicesList.stream()
                .mapToLong(FeedIndex::getScore)
                .max()
                .orElse(Integer.MAX_VALUE);
        setMaxScore(maxScore);
    }

    private List<FeedIndex> parseFeedIndex() {
        if (StringUtils.isEmpty(getData())) {
            return new ArrayList<>();
        }
        return SerializeUtil.deserialize(getData(), new TypeReference<>() {
        });
    }

    public List<FeedIndex> take(Long score, Integer size) {
        return parseFeedIndex().stream()
                .sorted(Comparator.comparing(FeedIndex::getScore).reversed())
                .filter(feedIndex -> feedIndex.getScore() < score)
                .limit(size)
                .collect(Collectors.toList());
    }
}
