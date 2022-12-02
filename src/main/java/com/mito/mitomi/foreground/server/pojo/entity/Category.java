package com.mito.mitomi.foreground.server.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {

    private Long id;//记录id

    private String name;//类别名称

    private Long parentId;//父级类别的id,如果无父级,则为0

    private Integer depth;//深度,最顶级类别的深度为1,次级为2,以此类推

    private String keywords;//关键词列表

    private Integer sort;//自定义排序序号

    private String icon;//图标图片的URL

    private Integer enable;//是否启用,1=启用,0=未启用

    private Integer isParent;//是否为父级(是否包含子级),1=是父级,0=不是父级

    private Integer isDisplay;//是否显示在导航栏中，1=启用，0=未启用

    private LocalDateTime gmtCreate;//数据创建时间

    private LocalDateTime gmtModified;//数据最后修改时间
}
