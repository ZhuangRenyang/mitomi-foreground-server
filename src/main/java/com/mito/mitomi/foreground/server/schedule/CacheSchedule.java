package com.mito.mitomi.foreground.server.schedule;

import com.mito.mitomi.foreground.server.mapper.CommodityMapper;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import com.mito.mitomi.foreground.server.repo.ICommodityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * 计划执行类
 * 此类为了减少服务器压力,将MySQL写入Redis
 */
@Slf4j
@Component
public class CacheSchedule {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ICommodityRepository commodityRepository;

    private static final long UPDATE_TIME = 1*60*60;
    /**
     * 计划任务，默认不开启，需要配置类进行配置
     */
    // cron = "秒 分 时 日 月 周"
    // cron = "56 23 18 ? * MON" 表示:每个月的周一(无视几号)的18:23:56将执行此任务
    // @Scheduled(cron = "56 23 18 ? * MON")
    @Scheduled(fixedRate = UPDATE_TIME * 60 * 1000)
    public void updateCache() {
        log.debug("执行计划任务，更新缓存中的商品列表...");
        // 将redis中商品列表清除
        commodityRepository.commodityDeleteList();
        //从mysql中读取商品列表
        List<CommodityListItemVO> commodityList = commodityMapper.commodityList();
        //将商品列表写入到redis
        commodityRepository.commodityPutList(commodityList);

    }
}
