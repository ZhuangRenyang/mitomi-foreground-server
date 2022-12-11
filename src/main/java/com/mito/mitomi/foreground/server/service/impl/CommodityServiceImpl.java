package com.mito.mitomi.foreground.server.service.impl;

import com.mito.mitomi.foreground.server.config.BeanConfig;
import com.mito.mitomi.foreground.server.controller.UploadController;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class CommodityServiceImpl implements ICommodityService {
    @Value("${dirPath}")
    private String dirPath;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ICommodityRepository commodityRepository;

    /*
     *添加商品
     */
    @Override
    public void commodityInsertDTO(CommodityInsertDTO commodityInsertDTO) {
        log.debug("执行到添加商品的方法:commodityInsertDTO");
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
        switch (commodityInsertRows){
            case 0:{
                String message = "添加商品失败,服务器忙,请稍后重试!";
                throw new ServiceException(ServiceCode.ERR_INSERT,message);//错误：插入失败
            }
            case 1:{
                log.debug("商品添加后,商品列表更新了");
                // 将redis中商品列表清除
                commodityRepository.commodityDeleteList();
                //从mysql中读取商品列表
                List<CommodityListItemVO> commodityList = commodityMapper.commodityList();
                //将商品列表写入到redis
                commodityRepository.commodityPutList(commodityList);
            }
        }
    }

    /*
     *删除商品
     */
    @Override
    public void deleteCommodityById(Long id) {
        log.debug("执行到删除商品的方法:deleteCommodityById");
        //根据id查询是否有该商品
        CommodityDetailVO commodityDetailVO = commodityMapper.getCommodityById(id);
        if (commodityDetailVO == null){
            String message = "删除商品失败,删除的数据(id:"+id+")不存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,message);
        }

        String commodityLogoName = commodityMapper.selectCommodityLogoById(id);
        log.debug("当前名称为:{}的图片删除了",commodityLogoName);
        if (commodityLogoName != null){
            String filePath =dirPath+"/"+ commodityLogoName;
            new File(filePath).delete();
            log.debug("路径为:{}的图片删除成功",filePath);
        }
        //调用mapper删除方法并返回值
        int commodityDeleteRows = commodityMapper.deleteCommodityById(id);
        switch (commodityDeleteRows){
            case 0:{
                String message = "删除失败,服务器忙,请稍后重试";
                throw new ServiceException(ServiceCode.ERR_DELETE,message);
            }
            case 1:{
                log.debug("商品删除后,商品列表更新了");
                // 将redis中商品列表清除
                commodityRepository.commodityDeleteList();
                //从mysql中读取商品列表
                List<CommodityListItemVO> commodityList = commodityMapper.commodityList();
                //将商品列表写入到redis
                commodityRepository.commodityPutList(commodityList);
            }
        }
    }

    /*
     *修改商品
     */
    @Override
    public void updateCommodityNameById(Long id, String name) {
        log.debug("执行到修该名称的方法:updateCommodityNameById");
        //根据id查询是否有该商品
        CommodityDetailVO commodityDetailVO = commodityMapper.getCommodityById(id);
        if (commodityDetailVO == null){
            String message = "修改商品名称失败，修改的数据(id:" + id + ")不存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        int commodityUpdateNameRows = commodityMapper.countCommodityByName(name);
        if (commodityUpdateNameRows == 1){
            String message = "修改商品名称失败，修改的数据(名称:" + name + ")已存在";
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, message);
        }
        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setName(name);
        commodity.setGmtModified(BeanConfig.localDateTime());
        int commodityUpdateRows = commodityMapper.updateCommodityNameById(commodity);
        switch (commodityUpdateRows){
            case 0:{
                String message = "修改商品名称失败，服务器忙，请稍后重试~";
                throw new ServiceException(ServiceCode.ERR_DELETE, message);
            }
            case 1:{
                log.debug("商品修改后,商品列表更新了");
                // 将redis中商品列表清除
                commodityRepository.commodityDeleteList();
                //从mysql中读取商品列表
                List<CommodityListItemVO> commodityList = commodityMapper.commodityList();
                //将商品列表写入到redis
                commodityRepository.commodityPutList(commodityList);
            }
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
