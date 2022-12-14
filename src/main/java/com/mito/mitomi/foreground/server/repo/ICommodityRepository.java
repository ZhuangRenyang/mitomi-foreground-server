package com.mito.mitomi.foreground.server.repo;

import com.mito.mitomi.foreground.server.pojo.vo.CommodityDetailVO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;

import java.util.List;

public interface ICommodityRepository {
    String KEY_PREFIX_COMMODITY_ITEM = "commodity:item:";

    String KEY_COMMODITY_LIST = "commodity:list";

    //存入商品数据
    void commodityPut(CommodityDetailVO commodityDetailVO);
    void commodityPutList(List<CommodityListItemVO> list);


    //删除商品数据
    void commodityDeleteItem(Long id);
    void commodityDeleteList();

    //取出商品数据
    CommodityDetailVO commodityGet(Long id);
    List<CommodityListItemVO> commodityGetList();

}
