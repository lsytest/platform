/*
 * 类名称:DistributorsEntity.java
 * 包名称:com.platform.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-01-20 20:28:54        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同科技
 */
package com.platform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author lipengjun
 * @date 2020-01-20 20:28:54
 */
@Data
@TableName("specific_distributors")
public class DistributorsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private Integer id;
    /**
     * 分销商名称
     */
    private String name;
    /**
     * 分销商描述
     */
    private String description;
    /**
     * 分销商图标
     */
    private String image;
    /**
     * 分销商地址
     */
    private String address;
    /**
     * 父级ID
     */
    private Integer parentId;
    /**
     * 创建时间
     */
    private Date createTime;

    private Integer state;

    private Double money;

    private List ids;
}
