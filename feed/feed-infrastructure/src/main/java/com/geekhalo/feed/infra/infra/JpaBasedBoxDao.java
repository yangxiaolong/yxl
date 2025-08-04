package com.geekhalo.feed.infra.infra;

import com.geekhalo.feed.domain.box.BoxDao;
import com.geekhalo.feed.domain.box.BoxType;
import com.geekhalo.feed.domain.feed.FeedIndex;
import com.geekhalo.feed.domain.feed.FeedOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Repository
public class JpaBasedBoxDao implements BoxDao {
    @Autowired
    private BoxItemsWrapperRepository boxItemsWrapperRepository;

    @Override
    public void append(FeedOwner feedOwner, BoxType boxType, FeedIndex feedIndex) {
        Optional<BoxItemsWrapper> itemsWrapperOpt = boxItemsWrapperRepository
                .getFirstMatch(
                feedOwner, boxType, feedIndex.getScore());
        if (itemsWrapperOpt.isPresent()) {
            BoxItemsWrapper boxItemsWrapper = itemsWrapperOpt.get();
            if (boxItemsWrapper.canAppend(boxType.getMaxPreRow(), feedIndex.getScore())){
                boxItemsWrapper.append(feedIndex);
                this.boxItemsWrapperRepository.save(boxItemsWrapper);
                return;
            }
        }
        BoxItemsWrapper boxItemsWrapper = BoxItemsWrapper.apply(feedOwner, boxType);
        boxItemsWrapper.append(feedIndex);
        this.boxItemsWrapperRepository.save(boxItemsWrapper);

    }

    @Override
    public void append(FeedOwner feedOwner, BoxType boxType, List<FeedIndex> feedIndices) {
        if (CollectionUtils.isEmpty(feedIndices)){
            return;
        }

        Long minScore = feedIndices.stream()
                .mapToLong(FeedIndex::getScore)
                .min()
                .orElse(Long.MIN_VALUE);

        Optional<BoxItemsWrapper> itemsWrapperOpt = boxItemsWrapperRepository
                .getFirstMatch(
                        feedOwner, boxType, minScore);
        if (itemsWrapperOpt.isPresent()) {
            BoxItemsWrapper boxItemsWrapper = itemsWrapperOpt.get();
            Iterator<FeedIndex> iterator = feedIndices.iterator();
            while (iterator.hasNext()) {
                FeedIndex feedIndex = iterator.next();
                if (boxItemsWrapper.canAppend(boxType.getMaxPreRow(), feedIndex.getScore())) {
                    boxItemsWrapper.append(feedIndex);
                    iterator.remove();
                }
            }
            this.boxItemsWrapperRepository.save(boxItemsWrapper);
        }

        List<BoxItemsWrapper> boxItemsWrappers = new ArrayList<>();
        BoxItemsWrapper boxItemsWrapper = null;
        for (FeedIndex feedIndex : feedIndices) {
            if (boxItemsWrapper == null
                    || !boxItemsWrapper.canAppend(boxType.getMaxPreRow(), feedIndex.getScore())) {
                boxItemsWrapper = BoxItemsWrapper.apply(feedOwner, boxType);
                boxItemsWrappers.add(boxItemsWrapper);
            }
            boxItemsWrapper.append(feedIndex);
        }
        boxItemsWrapperRepository.saveAll(boxItemsWrappers);
    }

    @Override
    public List<FeedIndex> load(FeedOwner feedOwner, BoxType boxType, Long score, Integer size) {
        Optional<BoxItemsWrapper> itemsWrapperOpt = boxItemsWrapperRepository
                .getFirstMatch(
                        feedOwner, boxType, score);
        if (!itemsWrapperOpt.isPresent()){
            return Collections.emptyList();
        }
        BoxItemsWrapper boxItemsWrapper = itemsWrapperOpt.get();
        List<FeedIndex> feedIndices = boxItemsWrapper.take(score, size);
        if (feedIndices.size() == size){
            return feedIndices;
        }

        int lost = size - feedIndices.size();
        Long minScore = boxItemsWrapper.getMinScore();

        Optional<BoxItemsWrapper> itemsWrapperOpt2 = boxItemsWrapperRepository
                .getFirstMatch(
                        feedOwner, boxType, minScore);
        if (itemsWrapperOpt2.isPresent()){
            List<FeedIndex> feedIndexList = itemsWrapperOpt2.get().take(minScore, lost);
            feedIndices.addAll(feedIndexList);
        }
        return feedIndices;
    }
}
