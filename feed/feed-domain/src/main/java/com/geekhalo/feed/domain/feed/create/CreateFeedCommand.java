package com.geekhalo.feed.domain.feed.create;

import com.geekhalo.feed.domain.feed.FeedData;
import com.geekhalo.feed.domain.feed.FeedOwner;
import com.lego.yxl.core.command.CommandForCreate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedCommand implements CommandForCreate {
    @NotNull
    private FeedOwner owner;
    @NotNull
    private FeedData data;

}
