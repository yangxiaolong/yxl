package com.lego.yxl.singlequery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangxiaolong
 * @create 2024/9/24
 */
@RestController
@RequestMapping(value = "/singlequery")
@Slf4j
public class SingleQueryController {

    @RequestMapping("/jpa")
    public String jpa() {
        return "jpa";
    }

}
