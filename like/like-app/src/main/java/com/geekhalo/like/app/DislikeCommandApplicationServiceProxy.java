package com.geekhalo.like.app;

import com.geekhalo.like.domain.dislike.DislikeAction;
import com.geekhalo.like.domain.dislike.DislikeActionRepository;
import com.lego.yxl.command.core.CommandApplicationServiceDefinition;

@CommandApplicationServiceDefinition(
        domainClass = DislikeAction.class,
        repositoryClass = DislikeActionRepository.class)
public interface DislikeCommandApplicationServiceProxy extends DislikeCommandApplicationService{

}
