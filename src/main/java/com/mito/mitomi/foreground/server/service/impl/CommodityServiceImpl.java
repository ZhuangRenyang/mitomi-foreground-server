package com.mito.mitomi.foreground.server.service.impl;

import com.mito.mitomi.foreground.server.config.BeanConfig;
import com.mito.mitomi.foreground.server.exception.ServiceException;
import com.mito.mitomi.foreground.server.mapper.CommodityMapper;
import com.mito.mitomi.foreground.server.pojo.dto.CommodityInsertDTO;
import com.mito.mitomi.foreground.server.pojo.entity.Commodity;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityDetailVO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import com.mito.mitomi.foreground.server.repo.ICommodityRepository;
import com.mito.mitomi.foreground.server.service.ICommodityService;
import com.mito.mitomi.foreground.server.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommodityServiceImpl implements ICommodityService {
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ICommodityRepository commodityRepository;

    /*
     *添加商品
     */
    @Override
    public void commodityInsertDTO(CommodityInsertDTO commodityInsertDTO) {
        //检查商品名称是否被占用
        String commodityInsertName = commodityInsertDTO.getName();//获取商品的名称
        int commodityCount = commodityMapper.countCommodityByName(commodityInsertName);//查询是否有该名称
        if (commodityCount>0){
            String message = "添加失败,商品名称[" + commodityInsertName + "]已存在";
            log.error(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT,message);//错误：冲突 - 重复数据
        }
        //创建实体对象(Mapper的方法参数是实体类型)
        Commodity commodity = new Commodity();
        //将当前方法参数的值复制到commodity  实体类型的对象中
        BeanUtils.copyProperties(commodityInsertDTO,commodity);//类型转换赋值
        commodity.setGmtCreate(BeanConfig.localDateTime());
        int commodityInsertRows = commodityMapper.insertCommodity(commodity);
        if (commodityInsertRows != 1){
            String message = "添加商品失败,服务器忙,请稍后重试!";
            throw new ServiceException(ServiceCode.ERR_INSERT,message);//错误：插入失败
        }
    }

    /*
     *删除商品
     */
    @Override
    public void deleteCommodityById(Long id) {
        //根据id查询是否有该商品
        CommodityDetailVO commodityDetailVO = commodityMapper.getCommodityById(id);
        if (commodityDetailVO == null){
            String message = "删除商品失败,删除的数据(id:"+id+")不存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        //调用mapper删除方法并返回值
        int commodityDeleteRows = commodityMapper.deleteCommodityById(id);
        if (commodityDeleteRows != 1){
            String message = "删除失败,服务器忙,请稍后重试";
            throw new ServiceException(ServiceCode.ERR_DELETE,message);
        }
    }

    /*
     *修改商品
     */
    @Override
    public void updateCommodityNameById(Long id, String name) {
        //根据id查询是否有该商品
        CommodityDetailVO commodityDetailVO = commodityMapper.getCommodityById(id);
        if (commodityDetailVO == null){
            String message = "修改品牌名称失败，修改的数据(id:" + id + ")不存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }

        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setName(name);
        commodity.setGmtModified(BeanConfig.localDateTime());
        int commodityUpdateRows = commodityMapper.updateCommodityNameById(commodity);
        if (commodityUpdateRows!=1){
            String message = "修改商品名称失败，服务器忙，请稍后重试~";
            throw new ServiceException(ServiceCode.ERR_DELETE, message);
        }
    }

    /*
     *查询商品
     */
    @Override
    public List<CommodityListItemVO> commodityList() {
        log.debug("处理查询品牌列表的业务...");
       return commodityRepository.commodityGetList();
    }


}
