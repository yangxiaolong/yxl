package com.geekhalo.like.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TargetCountQueryApi {
    String PATH = "feignService/targetCount/query";

    @GetMapping("getLikeCountByTarget")
    @PostMapping("getLikeCountByTarget")
    List<TargetCountVO> getLikeCountByTarget(@RequestParam String type,
                                             @RequestParam List<Long> ids);

    @GetMapping("getDislikeCountByType")
    @PostMapping("getDislikeCountByType")
    List<TargetCountVO> getDislikeCountByType(@RequestParam String type,
                                              @RequestParam List<Long> ids);
}
