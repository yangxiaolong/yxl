package com.geekhalo.like.app;

import com.geekhalo.like.domain.like.LikeAction;
import com.geekhalo.like.domain.like.LikeActionRepository;
import com.lego.yxl.core.command.CommandApplicationServiceDefinition;

@CommandApplicationServiceDefinition(
        domainClass = LikeAction.class,
        repositoryClass = LikeActionRepository.class
)
public interface LikeCommandApplicationServiceProxy extends LikeCommandApplicationService{

}
