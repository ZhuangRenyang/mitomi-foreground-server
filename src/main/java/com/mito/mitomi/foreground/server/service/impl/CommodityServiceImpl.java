package com.mito.mitomi.foreground.server.service.impl;

import com.mito.mitomi.foreground.server.mapper.CommodityMapper;
import com.mito.mitomi.foreground.server.service.ICommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommodityServiceImpl implements ICommodityService {
    @Autowired
    private CommodityMapper commodityMapper;
}
