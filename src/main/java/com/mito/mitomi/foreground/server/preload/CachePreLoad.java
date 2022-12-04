package com.mito.mitomi.foreground.server.preload;

import com.mito.mitomi.foreground.server.mapper.CommodityMapper;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import com.mito.mitomi.foreground.server.repo.ICommodityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

/*
 *第一次执行MySQL写入Redis
 * 此类为了减少服务器压力,将MySQL写入Redis
 */
@Slf4j
//@Component
public class CachePreLoad implements ApplicationRunner{
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ICommodityRepository commodityRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("执行CachePreLoad.run()方法");
        // 将redis中商品列表清除
        // 将redis中商品列表清除
        commodityRepository.commodityDeleteList();
        //从mysql中读取商品列表
        List<CommodityListItemVO> commodityList = commodityMapper.commodityList();
        //将商品列表写入到redis
        commodityRepository.commodityPutList(commodityList);
    }
}
