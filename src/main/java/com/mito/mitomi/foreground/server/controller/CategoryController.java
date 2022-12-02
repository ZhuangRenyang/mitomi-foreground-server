package com.mito.mitomi.foreground.server.controller;

import com.mito.mitomi.foreground.server.service.ICategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = "1.类别管理模块")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    //创建防止以后误创构造器
    public CategoryController() {
        log.debug("创建控制器对象.CategoryController");
    }

    @Autowired
    private ICategoryService categoryService;

}
