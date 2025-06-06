package com.geekhalo.like.feign.service;

import com.geekhalo.like.api.ActionCommandApi;
import com.geekhalo.like.api.ActionCommandParam;
import com.geekhalo.like.app.DislikeCommandApplicationService;
import com.geekhalo.like.app.LikeCommandApplicationService;
import com.geekhalo.like.domain.dislike.DislikeActionCommand;
import com.geekhalo.like.domain.dislike.UndislikeActionCommand;
import com.geekhalo.like.domain.like.LikeActionCommand;
import com.geekhalo.like.domain.like.UnlikeActionCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ActionCommandApi.PATH)
@Validated
public class ActionCommandFeignService implements ActionCommandApi {
    @Autowired
    private LikeCommandApplicationService likeCommandApplicationService;
    @Autowired
    private DislikeCommandApplicationService dislikeCommandApplicationService;

    @Override
    @PostMapping("like")
    public void like(@RequestBody @Valid ActionCommandParam param) {
        LikeActionCommand command = LikeActionCommand.apply(param.getUserId(), param.getTargetType(), param.getTargetId());
        this.likeCommandApplicationService.like(command);
    }

    @Override
    @PostMapping("unlike")
    public void unLike(@RequestBody @Valid ActionCommandParam param) {
        UnlikeActionCommand command = UnlikeActionCommand.apply(param.getUserId(), param.getTargetType(), param.getTargetId());
        this.likeCommandApplicationService.unLike(command);
    }

    @Override
    @PostMapping("dislike")
    public void dislike(@RequestBody @Valid ActionCommandParam param) {
        DislikeActionCommand command = DislikeActionCommand.apply(param.getUserId(), param.getTargetType(), param.getTargetId());
        this.dislikeCommandApplicationService.dislike(command);
    }

    @Override
    @PostMapping("unDislike")
    public void unDislike(@RequestBody @Valid ActionCommandParam param) {
        UndislikeActionCommand command = UndislikeActionCommand.apply(param.getUserId(), param.getTargetType(), param.getTargetId());
        this.dislikeCommandApplicationService.unDislike(command);
    }

}
