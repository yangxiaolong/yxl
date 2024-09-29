package com.lego.yxl.feign.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface TestFeignApi {
    @PostMapping("/test/postData/{key}")
    void postData(@PathVariable String key, @RequestBody List<Long> data);

    @PostMapping("/test/postDataForError/{key}")
    void postDataForError(@PathVariable String key, @RequestBody List<Long> data);

    @GetMapping("/test/getData/{key}")
    List<Long> getData(@PathVariable String key);

    @GetMapping("/test/getDataForError/{key}")
    List<Long> getDataForError(@PathVariable String key);

    @GetMapping("/test/customException")
    void customException();
}
