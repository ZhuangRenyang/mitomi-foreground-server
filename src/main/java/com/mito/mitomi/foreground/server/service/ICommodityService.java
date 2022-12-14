package com.mito.mitomi.foreground.server.service;

import com.mito.mitomi.foreground.server.pojo.dto.CommodityInsertDTO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;

import java.util.List;

public interface ICommodityService {

    /**
     * 添加商品 commodityInsertDTO
     * @param commodityInsertDTO 商品数据
     */

    void commodityInsertDTO(CommodityInsertDTO commodityInsertDTO);

    /**
     * 根据id删除商品数据
     * @param id 商品id
     */
    void deleteCommodityById(Long id);

    /**
     * 根据id 修改商品名称
     * @param id 商品id
     * @param name 商品名称
     */
    void updateCommodityNameById(Long id, String name);

    /**
     * 商品列表
     * @return 商品列表
     */
    List<CommodityListItemVO> commodityList();

    List<CommodityListItemVO> commodityCategoryList(String category);
}
