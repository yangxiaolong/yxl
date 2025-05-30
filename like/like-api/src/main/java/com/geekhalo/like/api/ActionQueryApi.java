package com.geekhalo.like.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ActionQueryApi {
    String PATH = "feignService/action/query";

    @GetMapping("getLikeByUserAndType")
    List<ActionVO> getLikeByUserAndType(Long userId, String type);

    @GetMapping("getDislikeByUserAndType")
    List<ActionVO> getDislikeByUserAndType(Long userId, String type);
}
