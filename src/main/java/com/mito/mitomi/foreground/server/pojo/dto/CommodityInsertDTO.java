package com.mito.mitomi.foreground.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增商品DTO类
 *  添加set&get / equals&hashcode / toString 方法
 */
@Data
public class CommodityInsertDTO implements Serializable {

    @ApiModelProperty(value = "商品名称",required = true,example ="苹果13")
    @NotBlank(message = "请填写有效的品牌名称-NotBlank")
    private String name;

    @ApiModelProperty(value = "商品分类",required = true,example = "数码")
    @NotNull(message = "请填写有效的商品分类")
    private String category;

    @ApiModelProperty(value = "商品简介",example = "专注技术几十年")
    private String description;

    @ApiModelProperty(value = "商品价格",example = "1999")
    private Double price;

    @ApiModelProperty(value = "关键词列表(关键词之间逗号分割)",example = "苹果,5G,苹果13")
    private String keywords;

    @ApiModelProperty(value = "商品logo",required = true,example = "http://p1.music/109.jpg")
    private String logo;

    @ApiModelProperty(value = "自定义排序符号",example = "110")
    @Range(min = 0,max = 99,message = "自定义排序值必须在0~99之间")
    private String sort;
}
