package com.mito.mitomi.foreground.server.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情VO
 *
 * @author java@mito.com
 * @version 0.0.1
 */
@Data
public class CommodityDetailVO implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /*
     *商品分类
     */
    private String category;

    /**
     * 商品名称的拼音
     */
    private Double price;

    /**
     * 商品logo的URL
     */
    private String logo;

    /**
     * 商品简介
     */
    private String description;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 自定义排序序号
     */
    private Integer sort;

    /**
     * 销量（冗余）
     */
    private Integer sales;

    /**
     * 商品种类数量总和（冗余）
     */
    private Integer productCount;

    /**
     * 买家评论数量总和（冗余）
     */
    private Integer commentCount;

    /**
     * 买家好评数量总和（冗余）
     */
    private Integer positiveCommentCount;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;
}
