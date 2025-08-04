package com.lego.yxl.enums.controller;

import com.lego.yxl.core.enums.mvc.CommonEnumVO;
import com.lego.yxl.core.enums.mvc.RestResult;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2025/5/27
 */
@RestController
@RequestMapping("enum")
public class EnumController {
    @GetMapping("paramToEnum")
    public RestResult<CommonEnumVO> paramToEnum(@RequestParam("newsStatus") NewsStatus newsStatus) {
        return RestResult.success(CommonEnumVO.from(newsStatus));
    }

    @GetMapping("pathToEnum/{newsStatus}")
    public RestResult<CommonEnumVO> pathToEnum(@PathVariable("newsStatus") NewsStatus newsStatus) {
        return RestResult.success(CommonEnumVO.from(newsStatus));
    }

    @PostMapping("jsonToEnum")
    public RestResult<CommonEnumVO> jsonToEnum(@RequestBody NewsStatusRequestBody newsStatusRequestBody) {
        return RestResult.success(CommonEnumVO.from(newsStatusRequestBody.getNewsStatus()));
    }

    @GetMapping("bodyToJson")
    public RestResult<NewsStatusResponseBody> bodyToJson() {
        NewsStatusResponseBody newsStatusResponseBody = new NewsStatusResponseBody();
        newsStatusResponseBody.setNewsStatus(Arrays.asList(NewsStatus.values()));
        return RestResult.success(newsStatusResponseBody);
    }

    @Data
    public static class NewsStatusRequestBody {
        private NewsStatus newsStatus;
    }

    @Data
    public static class NewsStatusResponseBody {
        private List<NewsStatus> newsStatus;
    }

}

