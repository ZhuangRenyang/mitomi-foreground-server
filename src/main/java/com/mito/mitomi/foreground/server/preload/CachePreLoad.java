package com.mito.mitomi.foreground.server.preload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/*
 *第一次执行MySQL写入Redis
 * 此类为了减少服务器压力,将MySQL写入Redis
 */
@Slf4j
//@Component
public class CachePreLoad{// implements ApplicationRunner
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//    }
}
