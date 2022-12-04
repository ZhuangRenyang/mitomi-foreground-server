package com.mito.mitomi.foreground.server.repo.impl;

import com.mito.mitomi.foreground.server.pojo.vo.CommodityDetailVO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import com.mito.mitomi.foreground.server.repo.ICommodityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CommodityRepositoryImpl implements ICommodityRepository {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    //存入商品数据
    @Override
    public void commodityPut(CommodityDetailVO commodityDetailVO) {
        String key = KEY_PREFIX_COMMODITY_ITEM + commodityDetailVO.getId();
        redisTemplate.opsForValue().set(key, commodityDetailVO);
    }

    @Override
    public void commodityPutList(List<CommodityListItemVO> list) {
        log.debug("向redis中写入商品列表数据:{}", list);
        for (CommodityListItemVO commodityListItemVO : list) {
            redisTemplate.opsForList().rightPush(KEY_COMMODITY_LIST,commodityListItemVO);
        }
    }


    //删除商品数据
    @Override
    public void commodityDeleteItem(Long id) {
        String key = KEY_COMMODITY_LIST + id;
        redisTemplate.delete(key);
    }

    @Override
    public void commodityDeleteList() {
        redisTemplate.delete(KEY_COMMODITY_LIST);
    }


    //取出商品数据
    @Override
    public CommodityDetailVO commodityGet(Long id) {
        String key = KEY_PREFIX_COMMODITY_ITEM + id;
        CommodityDetailVO commodityDetailVO = null;
        Serializable serializable = redisTemplate.opsForValue().get(key);
        if (serializable != null) {
            if (serializable instanceof CommodityDetailVO) {
                commodityDetailVO = (CommodityDetailVO) serializable;
            }
        }
        return commodityDetailVO;
    }

    @Override
    public List<CommodityListItemVO> commodityGetList() {
        log.debug("从redis中读取商品列表数据...");
        List<Serializable> list = redisTemplate.opsForList().range(KEY_COMMODITY_LIST,0,-1);
        List<CommodityListItemVO> commodityList = new ArrayList<>();
        for (Serializable serializable: list) {
            commodityList.add((CommodityListItemVO) serializable);
        }
        return commodityList;
    }


}
