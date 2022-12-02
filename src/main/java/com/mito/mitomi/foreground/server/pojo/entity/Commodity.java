package com.mito.mitomi.foreground.server.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Commodity {

    private Long id;//记录id

    private String name;//商品名称

    private String category;//商品分类

    private String logo;//商品logo的URL

    private String description;//商品简介

    private Double price;//商品价格

    private String keywords;//关键词列表，各关键词使用英文的逗号分隔

    private Integer sort;//自定义排序序号

    private Integer sales;//销量（冗余）

    private Integer productCount;//商品种类数量总和（冗余）

    private Integer commentCount;//买家评论数量总和（冗余）

    private Integer positiveCommentCount;//买家好评数量总和（冗余）

    private Integer enable;//是否启用，1=启用，0=未启用

    private LocalDateTime gmtCreate;//创建时间

    private LocalDateTime gmtModified;//最后修改时间
}
