package com.lego.yxl.core.enums.mvc.dict;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lego.yxl.core.enums.mvc.CommonEnumRegistry;
import com.lego.yxl.core.enums.mvc.CommonEnumVO;
import com.lego.yxl.core.enums.mvc.RestResult;
import com.lego.yxl.core.enums.CommonEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enumDict")
@Slf4j
public class EnumDictController {

    @Autowired
    private CommonEnumRegistry commonEnumRegistry;

    @GetMapping("all")
    public RestResult<Map<String, List<CommonEnumVO>>> allEnums() {
        Map<String, List<CommonEnum>> dict = this.commonEnumRegistry.getNameDict();
        Map<String, List<CommonEnumVO>> dictVo = Maps.newHashMapWithExpectedSize(dict.size());
        for (Map.Entry<String, List<CommonEnum>> entry : dict.entrySet()) {
            dictVo.put(entry.getKey(), CommonEnumVO.from(entry.getValue()));
        }
        return RestResult.success(dictVo);
    }

    @GetMapping("types")
    public RestResult<List<String>> enumTypes() {
        Map<String, List<CommonEnum>> dict = this.commonEnumRegistry.getNameDict();
        return RestResult.success(Lists.newArrayList(dict.keySet()));
    }

    @GetMapping("/{type}")
    public RestResult<List<CommonEnumVO>> dictByType(@PathVariable("type") String type) {
        Map<String, List<CommonEnum>> dict = this.commonEnumRegistry.getNameDict();
        List<CommonEnum> commonEnums = dict.get(type);

        return RestResult.success(CommonEnumVO.from(commonEnums));
    }
}
