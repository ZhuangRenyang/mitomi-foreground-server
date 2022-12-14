package com.mito.mitomi.foreground.server.mapper;

import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class CommodityMapperTest {

    @Autowired
    CommodityMapper commodityMapper;

    @Test
    public void commodityCategoryListTest(){
        String category = "数码";
        List<CommodityListItemVO> commodityListItemVO = commodityMapper.commodityCategoryList(category);
        log.debug("类别列表{}",commodityListItemVO);
    }
}
