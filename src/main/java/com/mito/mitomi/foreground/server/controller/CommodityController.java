package com.mito.mitomi.foreground.server.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.mito.mitomi.foreground.server.pojo.dto.CommodityInsertDTO;
import com.mito.mitomi.foreground.server.pojo.vo.CommodityListItemVO;
import com.mito.mitomi.foreground.server.security.LoginPrincipal;
import com.mito.mitomi.foreground.server.service.ICommodityService;
import com.mito.mitomi.foreground.server.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "1.商品管理模块")
@RestController
@RequestMapping("/commodity")//这个路径嗯;里面有图片么?sql数据库有路径这里没有图片名称
public class CommodityController {
    @Autowired
    private ICommodityService commodityService;
    //创建防止以后误创构造器
    public CommodityController() {
        log.debug("创建控制器对象.CategoryController");
    }

    //添加
    @ApiOperation("添加商品")
    @ApiOperationSupport(order = 10)
    @PostMapping("/insert")
    public JsonResult insert(@RequestBody @Valid CommodityInsertDTO commodityInsertDTO){
        log.debug("接收到添加商品的请求,参数:{}", commodityInsertDTO);
        commodityService.commodityInsertDTO(commodityInsertDTO);
        return JsonResult.ok();
    }

    //删除
    @ApiOperation("根据id删除商品")
    @ApiOperationSupport(order = 20)
    @PostMapping("/{id:[0-9]+}/delete")
    public JsonResult delete(@PathVariable Long id){
        log.debug("接收到删除商品的请求,参数:{}", id);
        commodityService.deleteCommodityById(id);
        return JsonResult.ok();
    }

    //修改
    @ApiOperation("修改商品")
    @ApiOperationSupport(order = 30)
    @PostMapping("/{id}/update/{name}")
    public JsonResult update(@PathVariable("id")Long id, @PathVariable("name") String name){
        log.info("接收到修改商品名称的请求,参数id:{},名称：{}", id,name);
        commodityService.updateCommodityNameById(id, name);
        return JsonResult.ok();
    }
  //你的那个图片路径在哪啊
    //查询
    @ApiOperation("查询商品列表")
    @ApiOperationSupport(order = 40)
    @GetMapping("")
    public JsonResult list(@AuthenticationPrincipal LoginPrincipal principal){
        log.debug("接收到查询商品列表的请求");

        Long id = principal.getId();
        String username = principal.getUsername();
        log.debug("认证信息中:id:{},用户名:{}",id,username);
        List<CommodityListItemVO> list = commodityService.commodityList();
        return JsonResult.ok(list);
    }

    @ApiOperationSupport(order = 50)
    @GetMapping("/mitomi/{category}")
    public JsonResult mitomiCategoryList(@PathVariable String category){
        log.debug("查询{}分类列表",category);
        List<CommodityListItemVO> commodityCategoryList = commodityService.commodityCategoryList(category);
        return JsonResult.ok(commodityCategoryList);
    }
}
