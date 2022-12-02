package com.mito.mitomi.foreground.server.service.impl;

import com.mito.mitomi.foreground.server.mapper.CategoryMapper;
import com.mito.mitomi.foreground.server.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

}
