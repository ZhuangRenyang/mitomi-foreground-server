package com.mito.mitomi.foreground.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@MapperScan("com.mito.mitomi.foreground.server.mapper")
//@Configuration
public class MybatisConfiguration {
    public MybatisConfiguration() {
        log.info("加载装配类,MybatisConfiguration");
    }
}
