package com.mito.mitomi.foreground.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    //创建防止以后误创构造器
    public CommodityController() {
        log.debug("创建控制器对象.CategoryController");
    }
}
